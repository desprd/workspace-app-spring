import React from "react";

function NoteCard({ note, checkAsDone }) {
  function handleIsDone(e) {
    e.stopPropagation();
    checkAsDone(note);
  }
  return (
    <div>
      <div className="px-5 py-4">
        <div
          onClick={() => console.log("Container clicked")}
          className=" cursor-pointer flex justify-between items-center px-5 py-4"
        >
          <div className="flex gap-5">
            <img
              className="w-12 h-12"
              src={note.status === "ACTIVE" ? "progress.png" : "done.png"}
              alt=""
            />
            <div className="flex gap-1 flex-col">
              <p className=" font-bold">{note.title}</p>
              <p className="text-graytext italic">{note.howLongAgoCreated}</p>
            </div>
          </div>
          {note.status === "ACTIVE" ? (
            <button>
              <img
                onClick={handleIsDone}
                className="w-7 h-7 cursor-pointer"
                src="tick.png"
                alt=""
              />
            </button>
          ) : (
            <button>
              <img
                onClick={() => console.log("Tick clicked")}
                className="w-7 h-7 cursor-pointer"
                src="cross.png"
                alt=""
              />
            </button>
          )}
        </div>
      </div>
    </div>
  );
}

export default NoteCard;
