import React from "react";
import SidePanel from "../components/SidePanel";

function Dashboard() {
  return (
    <div>
      <div className="grid grid-cols-[25%_75%] min-h-screen">
        <SidePanel pageName={"Dashboard"} />
      </div>
    </div>
  );
}

export default Dashboard;
