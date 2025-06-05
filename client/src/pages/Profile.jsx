import React, { useEffect, useState } from "react";
import SidePanel from "../components/SidePanel";
import { useAuth } from "../context/ContextProvider";
import axios from "axios";

function Profile() {
  const API_URL = import.meta.env.VITE_API_URL;
  const token = localStorage.getItem("token");
  const { username } = useAuth();
  const [name, setName] = useState("");
  const [jobTitle, setJobTitle] = useState("");
  const [email, setEmail] = useState("");
  const [company, setCompany] = useState("");
  const [imageURL, setImageURL] = useState("");
  const [imageFile, setImageFile] = useState(null);
  async function fetchData() {
    try {
      const response = await axios.get(`${API_URL}/profile/information`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      console.log(response.data);
      if (response.status == 200) {
        setName(response.data.username != null ? response.data.username : "");
        setJobTitle(
          response.data.jobTitle != null ? response.data.jobTitle : ""
        );
        setEmail(response.data.email != null ? response.data.email : "");
        setCompany(
          response.data.companyName != null ? response.data.companyName : ""
        );
        setImageURL(
          response.data.profilePictureURL != null
            ? response.data.profilePictureURL
            : ""
        );
      } else {
        console.log(
          "Something went wrong while fetching data" + response.status
        );
      }
    } catch (error) {
      console.log("Erorr" + error);
    }
  }

  function handleImageChange(e) {
    if (e.target.files && e.target.files[0]) {
      setImageFile(e.target.files[0]);
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageURL(reader.result);
      };
      reader.readAsDataURL(e.target.files[0]);
    }
  }
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      <div className="grid grid-cols-[25%_75%] min-h-screen gap-50">
        <SidePanel pageName={"Profile"} />
        <div className="mt-15 grid grid-cols-3">
          <div>
            <h1 className="text-3xl font-extrabold mb-10">Your Profile</h1>
            <div className="flex flex-row gap-5 mb-6">
              <img className="w-30 h-30 rounded-full" src={imageURL} alt="" />
              <div className="flex flex-col justify-center ">
                <p className="text-xl font-bold">{username}</p>
                <p className="text-graytext italic">
                  {jobTitle} | {company}
                </p>
              </div>
            </div>

            <form className="flex flex-col gap-6">
              <div>
                <label className="" htmlFor="username">
                  Choose image
                </label>
                <input
                  onChange={handleImageChange}
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
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="text"
                />
              </div>
              <div>
                <label className="" htmlFor="Email">
                  Email
                </label>
                <input
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="Email"
                />
              </div>
              <div>
                <label className="" htmlFor="JobTitle">
                  Job Title
                </label>
                <input
                  value={jobTitle}
                  onChange={(e) => setJobTitle(e.target.value)}
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type=""
                />
              </div>
              <div>
                <label className="" htmlFor="Company">
                  Company
                </label>
                <input
                  value={company}
                  onChange={(e) => setCompany(e.target.value)}
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
