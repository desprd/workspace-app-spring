# Workspace App

This repository contains a simple full-stack application composed of a Spring Boot backend and a React frontend.

## Project Structure

- **client** – React application bootstrapped with Vite. It contains the UI and uses React Router for navigation.
- **server** – Spring Boot application that exposes a REST API. It handles authentication using JWT and stores data in a PostgreSQL database.

## Backend

The server module provides several features:

- User registration and login with JWT based authentication.
- CRUD operations for personal notes.
- Profile information management with image uploads to Cloudinary.
- News headlines and weather forecast retrieval based on user preferences.

## Live Demo

- workspace-app-spring.vercel.app/dashboard

### Running the Server

Requirements:

- Java 21
- Maven (wrapper provided)

Environment variables from `application.properties` must be supplied, e.g. database credentials and API keys:

```
DB_URL, DB_USER, DB_PASSWORD
JWT_SECRET
CLOUDINARY_CLOUD_NAME, CLOUDINARY_API_KEY, CLOUDINARY_API_SECRET
NEWS_API_KEY
```

To start the server locally:

```bash
sh mvnw spring-boot:run
```

A Dockerfile is available and builds the packaged jar on port `8080`.

## Frontend

The client module is a React application styled with Tailwind CSS.

### Running the Client

Requirements:

- Node.js (with npm)

Install dependencies and start the development server:

```bash
cd client
npm install
npm run dev
```

The app will be available at `http://localhost:5173` by default.

To build for production:

```bash
npm run build
```

## Usage

1. Start the backend server.
2. Launch the client application.
3. Register a new account, then log in to manage notes, update your profile, and adjust news or forecast settings.

## License

This project is distributed without a specific license file.
