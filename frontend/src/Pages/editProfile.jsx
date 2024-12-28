import React, { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import "../CSS/EditProfile.css";

const EditProfile = () => {
    const [userData, setUserData] = useState(null);
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("accessToken");
        if (token) {
            fetch("http://localhost:8081/user/users/me", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    setUserData(data);
                    setIsLoading(false);
                    setError("");
                })
                .catch((error) => {
                    console.error("Error fetching user data:", error);
                    setError("Failed to fetch user data");
                    setIsLoading(false);
                });
        } else {
            setError("No token found");
            setIsLoading(false);
        }
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        if (userData) {
            setUserData({ ...userData, [name]: value });
        }
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("accessToken");
        if (!token) {
            setError("No token found");
            return;
        }
        setSuccessMessage("");
        setError("");

        const updatedData = {
            userId: userData.userId,
            username: userData.username,
            email: userData.email,
            name: userData.name,
            surname: userData.surname,
            phone: userData.phone,
            gender: userData.gender,
            interest: userData.interest,
        };

        if (password.trim()) {
            updatedData.password = password;
        }

        fetch("http://localhost:8081/user/users", {
            method: "PATCH",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                setSuccessMessage("Profile updated successfully!");
            })
            .catch((error) => {
                console.error("Error while updating profile:", error);
                setError(`Error while updating profile: ${error.message}`);
            });
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="edit-profile-container">
            <h2>Edit User Profile</h2>
            {successMessage && <div className="success-message">{successMessage}</div>}
            <form onSubmit={handleSubmit} className="edit-profile-form">
                <div>
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={userData.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="surname">Surname:</label>
                    <input
                        type="text"
                        id="surname"
                        name="surname"
                        value={userData.surname}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={userData.email}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="phone">Phone:</label>
                    <input
                        type="tel"
                        id="phone"
                        name="phone"
                        value={userData.phone}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="gender">Gender:</label>
                    <select
                        id="gender"
                        name="gender"
                        value={userData.gender}
                        onChange={handleInputChange}
                        required
                    >
                        <option value="">Select gender</option>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                        <option value="OTHER">Other</option>
                    </select>
                </div>
                <div>
                    <label htmlFor="interest">Interests:</label>
                    <textarea
                        id="interest"
                        name="interest"
                        value={userData.interest}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label htmlFor="password">Password: (To keep your password, leave it blank)</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={password}
                        onChange={handlePasswordChange}
                    />
                </div>
                <button type="submit" className="save-button">Save Changes</button>
                <Link to="/edit-location" className="edit-location-link">
                    Edit Location
                </Link>
            </form>
        </div>
    );
};

export default EditProfile;
