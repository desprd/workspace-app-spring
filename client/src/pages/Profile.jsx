import React, { useState } from "react";
import SidePanel from "../components/SidePanel";
import { useAuth } from "../context/ContextProvider";

function Profile() {
  const { username } = useAuth();
  const [name, setName] = useState();
  const [jobTitle, setJobTitle] = useState();
  const [email, setEmail] = useState();
  const [company, setCompany] = useState();

  return (
    <div>
      <div className="grid grid-cols-[25%_75%] min-h-screen gap-50">
        <SidePanel pageName={"Profile"} />
        <div className="mt-15 grid grid-cols-3">
          <div>
            <h1 className="text-3xl font-extrabold mb-10">Your Profile</h1>
            <div className="flex flex-row gap-5 mb-6">
              <img className="w-30 h-30 rounded-full" src="/user.png" alt="" />
              <div className="flex flex-col justify-center ">
                <p className="text-xl font-bold">{username}</p>
                <p className="text-graytext italic">
                  Java senior developer at Amazon
                  {/* TODO change on custom value */}
                </p>
              </div>
            </div>

            <form className="flex flex-col gap-6">
              <div>
                <label className="" htmlFor="username">
                  Choose image
                </label>
                <input
                  title="Choose image"
                  type="file"
                  accept="image/*"
                  className=" mt-2 file:px-4 file:py-2 file:bg-bluebutton file:text-white file:rounded-2xl"
                />
              </div>
              <div>
                <label className="" htmlFor="username">
                  Username
                </label>
                <input
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="text"
                />
              </div>
              <div>
                <label className="" htmlFor="Email">
                  Email
                </label>
                <input
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="Email"
                />
              </div>
              <div>
                <label className="" htmlFor="JobTitle">
                  Job Title
                </label>
                <input
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type=""
                />
              </div>
              <div>
                <label className="" htmlFor="Company">
                  Company
                </label>
                <input
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="Email"
                />
              </div>
              <button
                type="Submit"
                className="w-full mt-2 px-3 py-3 mb-5 rounded-xl bg-bluebutton font-bold text-white cursor-pointer"
              >
                Save
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
