import React, { useEffect, useState } from "react";
import axios from "axios";

function Requests() {

  const [requests, setRequests] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  useEffect(() => {
    fetchRequests();
  }, []);

  const fetchRequests = async () => {
    const response = await axios.get("http://localhost:8080/api/requests");
    setRequests(response.data);
  };

  const handleSearch = async () => {
    if (searchText.trim() === "") {
      fetchRequests();
      return;
    }

    const response = await axios.get(
      `http://localhost:8080/api/requests/search?name=${searchText}`
    );

    setRequests(response.data);
  };

  const handleFilterChange = async (e) => {
    const value = e.target.value;
    setStatusFilter(value);

    if (value === "") {
      fetchRequests();
      return;
    }

    const response = await axios.get(
      `http://localhost:8080/api/requests/filter?status=${value}`
    );

    setRequests(response.data);
  };

  const markCompleted = async (id) => {

    const amount = prompt("Enter bill amount:");

    if (!amount || isNaN(amount)) {
      alert("Please enter a valid amount.");
      return;
    }

    try {
      await axios.put(
        `http://localhost:8080/api/requests/complete/${id}?amount=${amount}`
      );

      fetchRequests();

    } catch (error) {
      alert("Error completing request");
      console.error(error);
    }
  };

  return (
    <div>
      <h1>Service Requests</h1>

      <div style={{ marginBottom: "15px" }}>
        <input
          type="text"
          placeholder="Search by customer name"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          style={{ padding: "5px", marginRight: "10px" }}
        />

        <button onClick={handleSearch} style={{ marginRight: "10px" }}>
          Search
        </button>

        <select
          value={statusFilter}
          onChange={handleFilterChange}
          style={{ padding: "5px", marginRight: "10px" }}
        >
          <option value="">All</option>
          <option value="PENDING">Pending</option>
          <option value="COMPLETED">Completed</option>
        </select>

        <button onClick={fetchRequests}>Reset</button>
      </div>

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Customer Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {requests.map((req) => (
            <tr key={req.requestId}>
              <td>{req.requestId}</td>
              <td>{req.customerName}</td>
              <td>{req.description}</td>
              <td>{req.status}</td>
              <td>
                {req.status === "PENDING" && (
                  <button
                    onClick={() => markCompleted(req.requestId)}
                    style={{ backgroundColor: "green", color: "white" }}
                  >
                    Mark Completed
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Requests;