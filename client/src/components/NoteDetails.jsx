import axios from "axios";
import React, { useEffect, useState } from "react";

function NoteDetails({ note, closeNoteDetails, updateNote }) {
  const token = localStorage.getItem("token");
  const [errorMessage, setErrorMessage] = useState("");
  const API_URL = import.meta.env.VITE_API_URL;
  const [noteTitle, setNoteTitle] = useState(note.title);
  const [content, setContent] = useState(note.content);
  function handleUpdate(e) {
    e.preventDefault();
    if (noteTitle === "") {
      setErrorMessage("Note title can't be empty");
      return;
    }
    updateNote(note, noteTitle, content);
  }
  return (
    <div className="fixed inset-0 bg-lightgray/60 flex justify-center items-center">
      <div className="bg-white p-18 relative">
        <button
          onClick={closeNoteDetails}
          className=" cursor-pointer absolute top-1 right-1"
        >
          <img className="w-8 h-8" src="cross.png" alt="" />
        </button>
        <form onSubmit={handleUpdate} className="flex flex-col gap-7" action="">
          {errorMessage && (
            <div className="text-red-600 text-[17px] text-center mb-5">
              {errorMessage}
            </div>
          )}
          <input
            type="text"
            value={noteTitle}
            onChange={(e) => setNoteTitle(e.target.value)}
            placeholder="Note Title"
            className="bg-lightgray py-2 px-5 rounded-xl w-full focus:outline-none"
          />
          <textarea
            type="text"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="bg-lightgray py-2 px-5 focus:outline-none rounded-xl p-2 h-full min-h-[300px] w-full min-w-[400px] resize-none "
          />
          <button
            type="Submit"
            className="w-full px-3 py-3 rounded-xl bg-bluebutton font-bold text-white cursor-pointer"
          >
            Update
          </button>
        </form>
      </div>
    </div>
  );
}

export default NoteDetails;
