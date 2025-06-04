import React from "react";
import { useNavigate } from "react-router-dom";

function SidePanelButton({ pageName, buttonName, leadTo }) {
  const navigate = useNavigate();
  return (
    <div>
      <button
        className={`${
          pageName === buttonName ? "bg-lightgray" : ""
        } w-full px-4 py-3 rounded-2xl text-left font-medium cursor-pointer`}
        onClick={() => navigate(leadTo)}
      >
        {buttonName}
      </button>
    </div>
  );
}

export default SidePanelButton;
