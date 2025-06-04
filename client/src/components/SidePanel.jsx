import React from "react";
import { useAuth } from "../context/ContextProvider";
import SidePanelButton from "./SidePanelButton";
import LogOutButton from "./LogOutButton";

function SidePanel({ pageName }) {
  const { username } = useAuth();
  return (
    <div>
      <div className="mt-15 ml-15 flex flex-col h-screen gap-7">
        <div className="flex flex-col">
          <h2 className="text-xl font-medium">{pageName}</h2>
          <p className="text-graytext">{username}</p>
        </div>
        <div className="flex flex-col gap-3 h-full">
          <SidePanelButton
            pageName={pageName}
            buttonName={"Dashboard"}
            leadTo={"/dashboard"}
          />
          <SidePanelButton
            pageName={pageName}
            buttonName={"Notes"}
            leadTo={"/notes"}
          />
          <SidePanelButton pageName={pageName} buttonName={"Profile"} />
          <SidePanelButton pageName={pageName} buttonName={"Settings"} />
        </div>
        <div className="mt-auto mb-35">
          <LogOutButton />
        </div>
      </div>
    </div>
  );
}

export default SidePanel;
