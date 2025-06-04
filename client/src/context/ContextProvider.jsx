import axios from "axios";
import React, { createContext, useContext, useEffect, useState } from "react";

const authContext = createContext();

function ContextProvider({ children }) {
  const API_URL = import.meta.env.VITE_API_URL;
  const [username, setUsername] = useState(() => {
    const stored = localStorage.getItem("username");
    return stored ? stored : null;
  });
  const [loading, setLoading] = useState(true);

  const login = (userData) => {
    setUsername(userData);
    localStorage.setItem("username", userData);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setUsername(null);
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
        const verifiedUser = response.data.username;
        setUsername(verifiedUser);
        localStorage.setItem("username", verifiedUser);
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
    <authContext.Provider value={{ username, login, logout }}>
      {children}
    </authContext.Provider>
  );
}

export const useAuth = () => useContext(authContext);

export default ContextProvider;
