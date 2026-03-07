import React, { useState } from "react";

function Login({ onLogin }) {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = () => {

    // Simple login (demo purpose)
    if (username === "admin" && password === "admin123") {
      onLogin("ADMIN");
    } 
    else if (username === "user" && password === "user123") {
      onLogin("USER");
    }
    else {
      alert("Invalid credentials");
    }
  };

  return (
    <div style={{ padding: "50px", textAlign: "center" }}>

      <h2>Login</h2>

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <br /><br />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <br /><br />

      <button onClick={handleLogin}>
        Login
      </button>

      <p style={{ marginTop: "20px", color: "gray" }}>
        Admin → admin / admin123 <br />
        User → user / user123
      </p>

    </div>
  );
}

export default Login;