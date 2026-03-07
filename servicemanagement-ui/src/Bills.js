import React, { useEffect, useState } from "react";
import axios from "axios";

function Bills() {

  const [bills, setBills] = useState([]);
  const [statusFilter, setStatusFilter] = useState("");

  useEffect(() => {
    fetchBills();
  }, []);

  const fetchBills = async () => {
    const response = await axios.get("http://localhost:8080/api/bills");
    setBills(response.data);
  };

  const handleFilterChange = async (e) => {
    const value = e.target.value;
    setStatusFilter(value);

    if (value === "") {
      fetchBills();
      return;
    }

    const response = await axios.get(
      `http://localhost:8080/api/bills/filter?status=${value}`
    );

    setBills(response.data);
  };

  const payBill = async (id) => {
    await axios.put(`http://localhost:8080/api/bills/pay/${id}`);
    fetchBills();
  };

  return (
    <div>
      <h1>Billing Management</h1>

      <div style={{ marginBottom: "15px" }}>
        <select
          value={statusFilter}
          onChange={handleFilterChange}
          style={{ padding: "6px", marginRight: "10px" }}
        >
          <option value="">All</option>
          <option value="UNPAID">Unpaid</option>
          <option value="PAID">Paid</option>
        </select>

        <button onClick={fetchBills}>Reset</button>
      </div>

      <table border="1" cellPadding="10" width="100%">
        <thead>
          <tr>
            <th>ID</th>
            <th>Customer Name</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Created At</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {bills.map((bill) => (
            <tr key={bill.id}>
              <td>{bill.id}</td>

              {/* FIX 1: Proper customer name handling */}
              <td>
                {bill.customerName}
              </td>

              <td>₹ {bill.amount}</td>

              <td>
                {bill.status === "PAID" ? (
                  <span style={{ color: "green", fontWeight: "bold" }}>
                    PAID
                  </span>
                ) : (
                  <span style={{ color: "red", fontWeight: "bold" }}>
                    UNPAID
                  </span>
                )}
              </td>

              <td>{bill.createdAt}</td>

              {/* FIX 2: Never leave Action column empty */}
              <td>
                {bill.status === "UNPAID" ? (
                  <button
                    onClick={() => payBill(bill.id)}
                    style={{
                      backgroundColor: "blue",
                      color: "white",
                      padding: "6px 10px",
                      border: "none",
                      borderRadius: "4px",
                      cursor: "pointer"
                    }}
                  >
                    Mark as Paid
                  </button>
                ) : (
                  <span style={{ color: "green", fontWeight: "bold" }}>
                    Paid
                  </span>
                )}
              </td>

            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Bills;