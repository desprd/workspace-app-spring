import React from "react";
import SidePanel from "../components/SidePanel";

function Notes() {
  return (
    <div>
      <div className="grid grid-cols-[25%_75%] min-h-screen gap-50">
        <SidePanel pageName={"Notes"} />
        <div className="mt-15 flex flex-col gap-10 pr-80">
          <div className="flex justify-between items-center">
            <h1 className="text-3xl font-extrabold">Notes</h1>
            <button className="bg-lightgray px-12 py-2 rounded-2xl">
              New note
            </button>
          </div>
          <div className="relative w-full ">
            <img
              src="search.png"
              alt="icon"
              className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5"
            />
            <input
              type="text"
              placeholder="Search notes"
              className="pl-12 focus:outline-none w-full px-5 py-3 rounded-2xl bg-lightgray"
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Notes;
