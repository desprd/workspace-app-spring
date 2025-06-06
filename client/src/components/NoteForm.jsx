import React from "react";

function NoteForm() {
  return (
    <div className="fixed inset-0 bg-lightgray/60 flex justify-center items-center">
      <div className="bg-white p-18 relative">
        <button className=" cursor-pointer absolute top-1 right-1">
          <img className="w-8 h-8" src="cross.png" alt="" />
        </button>
        <form className="flex flex-col gap-7" action="">
          <input
            type="text"
            //value={title}
            //onChange={(e) => setTitle(e.target.value)}
            placeholder="Note Title"
            className="bg-lightgray py-2 px-5 rounded-xl w-full focus:outline-none"
          />
          <textarea
            type="text"
            //value={description}
            //onChange={(e) => setDescription(e.target.value)}
            placeholder="Description"
            className="bg-lightgray py-2 px-5 focus:outline-none rounded-xl p-2 h-full min-h-[300px] w-full min-w-[400px] resize-none "
          />
          <button
            type="Submit"
            className="w-full px-3 py-3 rounded-xl bg-bluebutton font-bold text-white cursor-pointer"
          >
            Create
          </button>
        </form>
      </div>
    </div>
  );
}

export default NoteForm;
