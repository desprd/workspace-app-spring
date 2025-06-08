import axios from "axios";
import React, { createContext, useContext, useEffect, useState } from "react";

const authContext = createContext();

function ContextProvider({ children }) {
  const API_URL = import.meta.env.VITE_API_URL;
  const [username, setUsername] = useState(() => {
    const stored = localStorage.getItem("username");
    return stored ? stored : null;
  });
  const [preferences, setPreferences] = useState(() => {
    const stored = JSON.parse(localStorage.getItem("preferences"));
    return stored ? stored : null;
  });
  const [loading, setLoading] = useState(true);

  const login = (userData) => {
    setUsername(userData.username);
    setPreferences(userData.preferences);
    localStorage.setItem("username", userData.username);
    localStorage.setItem("preferences", JSON.stringify(userData.preferences));
  };

  const logout = () => {
    setUsername(null);
    setPreferences(null);
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("preferences");
    localStorage.clear();
  };

  const verifyUser = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        logout();
        return;
      }

      const response = await axios.get(`${API_URL}/auth/verify`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status == 200) {
        login(response.data);
      } else {
        logout();
      }
    } catch (error) {
      console.log("Verification error:", error);
      logout();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    verifyUser();
  }, []);

  if (loading) return <div>Loading secure session...</div>;

  return (
    <authContext.Provider value={{ username, login, logout, preferences }}>
      {children}
    </authContext.Provider>
  );
}

export const useAuth = () => useContext(authContext);

export default ContextProvider;
