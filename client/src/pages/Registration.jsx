import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/ContextProvider";

function Registration() {
  const API_URL = import.meta.env.VITE_API_URL;
  const [errorMessage, setErrorMessage] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [agreed, setAgreed] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();
  async function handleSubmit(e) {
    e.preventDefault();
    if (!agreed) {
      setErrorMessage("You have to click 'I agree' to continue");
      return;
    }
    if (password.length < 6) {
      setErrorMessage("Password supposed to be at least 6 characters long");
      return;
    }
    try {
      const response = await axios.post(`${API_URL}/auth/register`, {
        username: name,
        email,
        password,
      });
      console.log(response);
      if (response.status === 201) {
        localStorage.setItem("token", response.data.token);
        login(response.data);
        navigate("/dashboard");
      } else {
        setErrorMessage(response);
      }
    } catch (error) {
      setErrorMessage(error.response.data);
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
            {errorMessage && (
              <div className="text-red-600 text-[17px] text-center mb-5">
                {errorMessage}
              </div>
            )}
            <form className="flex flex-col gap-6" onSubmit={handleSubmit}>
              <div>
                <label className="" htmlFor="username">
                  Your username
                </label>
                <input
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="focus:outline-none w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
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
                  className="focus:outline-none w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="email"
                />
              </div>
              <div>
                <label className="" htmlFor="username">
                  Password
                </label>
                <input
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="focus:outline-none w-full mt-2 px-3 py-3 rounded-xl bg-lightgray"
                  type="password"
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
              <button
                type="Submit"
                className="w-full mt-2 px-3 py-3 rounded-xl bg-bluebutton font-bold text-white cursor-pointer"
              >
                Sign up
              </button>
            </form>
          </div>
        </div>
        <div className="flex justify-center">
          <div className="mt-15 flex flex-col gap-5">
            <p className="text-graytext">Already have an account?</p>
            <Link to="/login" className="self-center text-graytext">
              Sign in
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Registration;
