import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Registration() {
  const API_URL = import.meta.env.VITE_API_URL;
  const [errorMessage, setErrorMessage] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [agreed, setAgreed] = useState(false);
  const navigate = useNavigate();
  async function handleSubmit(e) {
    e.preventDefault();
    if (!agreed) {
      setErrorMessage("You have click 'I agree' to continue");
      return;
    }
    try {
      const respose = await axios.post(`${API_URL}/registration`, {
        name,
        email,
        password,
      });
      if (respose.status === 201) {
        navigate("/");
      }
    } catch (error) {
      console.log(error);
    }
  }
  return (
    <div>
      <div className="min-h-screen">
        <div className="flex justify-center">
          <h1 className="mt-15 mb-10 font-bold text-3xl">Create an account</h1>
        </div>
        <div className="grid grid-cols-3">
          <div className="ml-30">
            <form className="flex flex-col gap-6" onSubmit={handleSubmit}>
              <div>
                <label className="" htmlFor="username">
                  Your name
                </label>
                <input
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="text"
                />
              </div>

              <div>
                <label className="" htmlFor="username">
                  Your email
                </label>
                <input
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="text"
                />
              </div>
              <div>
                <label className="" htmlFor="username">
                  Password
                </label>
                <input
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="text"
                />
              </div>
              <div className="">
                <input
                  type="checkbox"
                  id="agree"
                  checked={agreed}
                  onChange={(e) => setAgreed(e.target.checked)}
                  className="mr-2"
                />
                <label htmlFor="agree">
                  I agree to the Terms of Service and Privacy Policy
                </label>
              </div>
              <button className="w-full mt-2 px-3 py-3 rounded-xl bg-bluebutton font-bold text-white">
                Sign up
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Registration;
