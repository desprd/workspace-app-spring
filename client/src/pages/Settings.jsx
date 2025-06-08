import React, { useState } from "react";
import SidePanel from "../components/SidePanel";
import { useAuth } from "../context/ContextProvider";
import axios from "axios";

function Settings() {
  const API_URL = import.meta.env.VITE_API_URL;
  const token = localStorage.getItem("token");
  const [errorMessage, setErrorMessage] = useState("");
  const { preferences } = useAuth();
  const { verifyUser } = useAuth();
  const [forecastEnable, setForecastEnable] = useState(
    preferences.forecastIsAllowed
  );
  const [newsEnable, setNewsEnable] = useState(preferences.newsAreAllowed);
  const [city, setCity] = useState(() => {
    const stored = preferences.city;
    return stored != null ? stored : "";
  });
  function handleForecastSwitch() {
    if (forecastEnable) {
      setForecastEnable(false);
    } else {
      setForecastEnable(true);
    }
  }
  function handleNewsSwitch() {
    if (newsEnable) {
      setNewsEnable(false);
    } else {
      setNewsEnable(true);
    }
  }
  async function savePreferences() {
    if (forecastEnable && (city === null || city === "")) {
      setErrorMessage("Please enter city name");
      return;
    }
    try {
      const response = await axios.put(
        `${API_URL}/preferences/update`,
        {
          forecastIsAllowed: forecastEnable,
          newsAreAllowed: newsEnable,
          city,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status === 200) {
        console.log("Settings updated successfully");
        verifyUser();
      } else {
        console.log("Updating failed " + response.data);
        setErrorMessage(response.data);
      }
    } catch (error) {
      console.log("Error " + error);
      setErrorMessage(error.response.data);
    }
  }
  return (
    <div>
      <div className="grid grid-cols-[25%_75%] min-h-screen gap-50">
        <SidePanel pageName={"Settings"} />
        <div className="mt-15 flex flex-col gap-10 pr-80">
          <h1 className="text-3xl font-extrabold">Preferences</h1>
          <div className="flex flex-col gap-5">
            {errorMessage && (
              <div className="text-red-600 text-[17px] mb-5">
                {errorMessage}
              </div>
            )}
            <div className="flex gap-5">
              <span className="text-xl">Enable weather forecast</span>
              <label className="inline-flex items-center cursor-pointer">
                <input
                  type="checkbox"
                  value=""
                  className="sr-only peer "
                  checked={forecastEnable}
                  onChange={handleForecastSwitch}
                />
                <div className="relative w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-0 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600 dark:peer-checked:bg-blue-600"></div>
              </label>
            </div>
            {forecastEnable && (
              <form action="" className="flex flex-col">
                <label className="" htmlFor="username">
                  City
                </label>
                <input
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                  className=" w-[300px] mt-2 px-3 py-3 rounded-xl bg-lightgray focus:outline-none"
                  type="text"
                />
              </form>
            )}
          </div>
          <div className="flex gap-5">
            <span className="text-xl">Enable news column</span>
            <label className="inline-flex items-center cursor-pointer">
              <input
                type="checkbox"
                value=""
                className="sr-only peer "
                checked={newsEnable}
                onChange={handleNewsSwitch}
              />
              <div className="relative w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-0 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600 dark:peer-checked:bg-blue-600"></div>
            </label>
          </div>
          <button
            onClick={savePreferences}
            className="w-[100px] mt-2 px-3 py-3 mb-5 rounded-xl bg-bluebutton font-bold text-white cursor-pointer"
          >
            Save
          </button>
        </div>
      </div>
    </div>
  );
}

export default Settings;
