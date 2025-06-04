import React from "react";
import { useAuth } from "../context/ContextProvider";
import { useNavigate } from "react-router-dom";

function LogOutButton() {
  const navigate = useNavigate();
  const { logout } = useAuth();
  function logOutHandler() {
    logout();
    navigate("/registration");
  }
  return (
    <div>
      <div>
        <button
          className={`w-full px-4 py-3 rounded-2xl text-left font-medium cursor-pointer`}
          onClick={logOutHandler}
        >
          Log out
        </button>
      </div>
    </div>
  );
}

export default LogOutButton;
