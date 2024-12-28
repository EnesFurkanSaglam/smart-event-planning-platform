import React, { useState } from "react";
import axios from "axios";
import '../CSS/ForgotPassword.css';

function ForgotPassword() {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
  });

  const [alertMessage, setAlertMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8081/refresh-password", formData);
      setAlertMessage("Password reset email sent successfully!");
    } catch (error) {
      setAlertMessage("Failed to send email. Please check the details.");
    }
  };

  return (
    <div className="forgot-password-container">
      <h2>Forgot Password</h2>
      <form onSubmit={handleSubmit} className="forgot-password-form">
        <div className="form-group">
          <label>Username:</label>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
            className="form-input"
            style={{ marginLeft: 50 }}
          />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            className="form-input"
            style={{ marginLeft: 85 }}
          />
        </div>
        <button type="submit" className="submit-button">Submit</button>
      </form>
      {alertMessage && <div className="alert-message">{alertMessage}</div>}
    </div>
  );
}

export default ForgotPassword;
