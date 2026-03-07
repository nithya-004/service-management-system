import React, { useEffect, useState } from "react";
import axios from "axios";

function Dashboard() {

  const [summary, setSummary] = useState({
    totalRequests: 0,
    pendingRequests: 0,
    completedRequests: 0,
    totalRevenue: 0,
    topEngineer: "N/A"
  });

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/requests/dashboard"
      );
      setSummary(response.data);
    } catch (error) {
      console.error("Error fetching dashboard data", error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Dashboard</h1>

      <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(2, 1fr)",
        gap: "20px",
        marginTop: "20px"
      }}>

        <div style={cardStyle}>
          <h3>Total Requests</h3>
          <p>{summary.totalRequests}</p>
        </div>

        <div style={cardStyle}>
          <h3>Pending Requests</h3>
          <p>{summary.pendingRequests}</p>
        </div>

        <div style={cardStyle}>
          <h3>Completed Requests</h3>
          <p>{summary.completedRequests}</p>
        </div>

        <div style={cardStyle}>
          <h3>Total Revenue</h3>
          <p>₹ {summary.totalRevenue}</p>
        </div>

        <div style={cardStyle}>
          <h3>Top Engineer</h3>
          <p>{summary.topEngineer}</p>
        </div>

      </div>
    </div>
  );
}

const cardStyle = {
  border: "1px solid #ccc",
  padding: "20px",
  borderRadius: "10px",
  textAlign: "center",
  backgroundColor: "#f9f9f9",
  fontSize: "18px",
  fontWeight: "bold"
};

export default Dashboard;