import React, { useEffect, useState } from "react";
import SidePanel from "../components/SidePanel";
import { useAuth } from "../context/ContextProvider";
import axios from "axios";
import NoteCard from "../components/NoteCard";
import NoteDetails from "../components/NoteDetails";

function Dashboard() {
  const API_URL = import.meta.env.VITE_API_URL;
  const token = localStorage.getItem("token");
  const { username } = useAuth();
  const [timeOfDay, setTimeOfDay] = useState("");
  const [todayNotes, setTodayNotes] = useState([]);
  const [isNoteDetailsOpen, setIsNoteDetailsOpen] = useState(false);
  const [noteToOpen, setNoteToOpen] = useState(null);
  function openNoteDetails(note) {
    setIsNoteDetailsOpen(true);
    setNoteToOpen(note);
  }
  function closeNoteDetails() {
    setIsNoteDetailsOpen(false);
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
          <div className="grid grid-cols-[50%_50%] min-h-screen gap-20">
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
