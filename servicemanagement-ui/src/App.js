import React from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import Login from "./Login";
import Dashboard from "./Dashboard";
import Layout from "./Layout";
import Bills from "./Bills";
import Requests from "./Requests";


function AppWrapper() {
  const navigate = useNavigate();

  // This function will be sent to Login
  const handleLogin = (role) => {
    console.log("Logged in as:", role);

    // After login → go to dashboard
    navigate("/dashboard");
  };

  return (
    <Routes>
  <Route path="/" element={<Login onLogin={handleLogin} />} />

  <Route
    path="/dashboard"
    element={
      <Layout>
        <Dashboard />
      </Layout>
    }
  />

  <Route
    path="/requests"
    element={
      <Layout>
        <div><Requests/></div>
      </Layout>
    }
  />

  <Route
    path="/bills"
    element={
      <Layout>
        <Bills />
      </Layout>
    }
  />
</Routes>
  );
}

function App() {
  return (
    <Router>
      <AppWrapper />
    </Router>
  );
}

export default App;
