import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/ContextProvider";

function Login() {
  const API_URL = import.meta.env.VITE_API_URL;
  const [errorMessage, setErrorMessage] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();
  async function handleSubmit(e) {
    e.preventDefault();
    try {
      const response = await axios.post(`${API_URL}/auth/login`, {
        username: name,
        password,
      });
      console.log(response);
      if (response.status === 200) {
        localStorage.setItem("token", response.data.token);
        login(response.data.username);
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
          <h1 className="mt-15 mb-10 font-bold text-3xl">Sign in</h1>
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
                  type="password"
                />
              </div>
              <button
                type="Submit"
                className="w-full mt-2 px-3 py-3 rounded-xl bg-bluebutton font-bold text-white cursor-pointer"
              >
                Sign in
              </button>
            </form>
          </div>
        </div>
        <div className="flex justify-center">
          <div className="mt-50 flex flex-col gap-5">
            <p className="text-graytext">Don't have an account yet?</p>
            <Link to="/registration" className="self-center text-graytext">
              Sign up
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
