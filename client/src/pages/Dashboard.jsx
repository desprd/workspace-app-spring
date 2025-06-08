import React, { useEffect, useState } from "react";
import SidePanel from "../components/SidePanel";
import { useAuth } from "../context/ContextProvider";
import axios from "axios";
import NoteCard from "../components/NoteCard";
import NoteDetails from "../components/NoteDetails";
import ForecastPanel from "../components/ForecastPanel";
import NewsCard from "../components/NewsCard";

function Dashboard() {
  const API_URL = import.meta.env.VITE_API_URL;
  const token = localStorage.getItem("token");
  const { username, preferences } = useAuth();
  const [timeOfDay, setTimeOfDay] = useState("");
  const [todayNotes, setTodayNotes] = useState([]);
  const [isNoteDetailsOpen, setIsNoteDetailsOpen] = useState(false);
  const [noteToOpen, setNoteToOpen] = useState(null);
  const [weather, setWeather] = useState(null);
  const [news, setNews] = useState([]);
  function openNoteDetails(note) {
    setIsNoteDetailsOpen(true);
    setNoteToOpen(note);
  }
  function closeNoteDetails() {
    setIsNoteDetailsOpen(false);
  }
  async function getNews() {
    try {
      const response = await axios.get(`${API_URL}/news/get`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      if (response.status === 200) {
        setNews(response.data);
      } else {
        console.log("Failed to fetch news ");
      }
    } catch (error) {
      console.log("Error " + error);
    }
  }
  async function getForecast() {
    try {
      const response = await axios.get(`${API_URL}/forecast/get`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      if (response.status === 200) {
        console.log("Weather successfully fetched ", response.data.current);
        setWeather(response.data.current);
      } else {
        console.log("Failed to fetch weather " + response.data);
      }
    } catch (error) {
      console.log("Error " + error);
    }
  }
  async function updateNote(note, noteTitle, content) {
    const id = note.id;
    try {
      const response = await axios.put(
        `${API_URL}/note/update/${id}`,
        {
          title: noteTitle,
          content,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (response.status === 200) {
        console.log("Note updated successfully");
        fetchUserData();
        setIsNoteDetailsOpen(false);
      } else {
        console.log("Failed to update the notes " + response.data);
      }
    } catch (error) {
      console.log("Error " + error);
    }
  }
  async function checkAsDone(note) {
    const id = note.id;
    try {
      const response = await axios.put(
        `${API_URL}/note/change/${id}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      console.log(response.data);
      if (response.status === 200) {
        fetchUserData();
        console.log("Something went wrong " + response.data);
      }
    } catch (error) {
      console.log("Error " + error);
    }
  }
  async function deleteNote(note) {
    const id = note.id;
    try {
      const response = await axios.delete(`${API_URL}/note/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (response.status === 200) {
        console.log("Note removed successfully");
        fetchUserData();
      } else {
        console.log("Failed to remove the note " + response.data);
      }
    } catch (error) {
      console.log("Error " + error);
    }
  }
  async function fetchUserData() {
    try {
      const response = await axios.get(`${API_URL}/note/get/today`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      console.log(response.data);
      if (response.status === 200) {
        console.log("User data fetched successfully");
        setTimeOfDay(response.data.timeOfDay);
        setTodayNotes(response.data.todayNotes);
      } else {
        console.log("Failed to fetch user data " + response.data);
      }
    } catch (error) {
      console.log("Error " + error);
    }
  }
  useEffect(() => {
    if (preferences.forecastIsAllowed) {
      getForecast();
    }
    if (preferences.newsAreAllowed) {
      getNews();
    }
    fetchUserData();
  }, []);
  return (
    <div>
      <div className="grid grid-cols-[25%_75%] min-h-screen gap-50">
        <SidePanel pageName={"Dashboard"} />
        <div className="mt-15 flex flex-col gap-20 pr-80">
          <h1 className="text-3xl font-extrabold">
            Good {timeOfDay}, {username}
          </h1>
          <div className="grid grid-cols-[50%_50%] min-h-screen gap-40">
            <div>
              <h2 className="text-graytext text-xl italic">Today notes</h2>
              {todayNotes.map((note) => (
                <NoteCard
                  note={note}
                  checkAsDone={checkAsDone}
                  deleteNote={deleteNote}
                  openNoteDetails={openNoteDetails}
                />
              ))}
            </div>
            <div className="flex flex-col gap-10">
              <div>{weather && <ForecastPanel weather={weather} />}</div>
              <div className="flex flex-col gap-4">
                <p className="text-2xl font-bold">Latest news</p>
                <div>
                  {news.length > 0 &&
                    news.map((article) => <NewsCard news={article} />)}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      {isNoteDetailsOpen && (
        <NoteDetails
          note={noteToOpen}
          closeNoteDetails={closeNoteDetails}
          updateNote={updateNote}
        />
      )}
    </div>
  );
}

export default Dashboard;
