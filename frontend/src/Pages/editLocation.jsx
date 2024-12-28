import React, { useState, useEffect } from "react";
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
import axios from "axios";
import '../CSS/editLocation.css';

const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

async function getCoordsForAddress(address) {
    try {
        const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(address)}&key=${API_KEY}`
        );
        const data = response.data;

        if (!data || data.status === "ZERO_RESULTS" || !Array.isArray(data.results) || data.results.length === 0) {
            alert("Couldn't find the address");
            return null;
        }

        const coordinates = data.results[0].geometry.location;
        return coordinates;
    } catch (error) {
        console.error("Error fetching coordinates:", error);
        alert("An error occurred while fetching coordinates.");
        return null;
    }
}

function EditLocation() {
    const [address, setAddress] = useState("");
    const [locationId, setLocationId] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState("");
    const [location, setLocation] = useState({ lat: null, lng: null });
    const [userData, setUserData] = useState(null);

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
                    if (data.location) {
                        const { locationId, address, latitude, longitude } = data.location;
                        setLocationId(locationId);
                        setLocation({ lat: latitude, lng: longitude });
                        setAddress(address || "");
                    }
                    setIsLoading(false);
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

    const handleAddressChange = (e) => {
        setAddress(e.target.value);
    };

    const handleUpdateCoordinates = async () => {
        if (!address) {
            alert("Please enter an address.");
            return;
        }
        const coordinates = await getCoordsForAddress(address);
        if (coordinates) {
            setLocation(coordinates);
        }
    };

    const handleSaveLocation = () => {
        const token = localStorage.getItem("accessToken");
        if (!token) {
            alert("No access token found. Please log in.");
            return;
        }

        if (!location.lat || !location.lng || !address) {
            alert("Please provide a valid address and update coordinates before saving.");
            return;
        }

        const locationData = {
            locationId,
            address,
            latitude: location.lat,
            longitude: location.lng,
        };

        fetch(`http://localhost:8081/location/locations/with-user/${userData.userId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(locationData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to save location");
                }
                return response.json();
            })
            .then((data) => {
                alert("Location saved successfully!");
            })
            .catch((error) => {
                console.error("Error saving location:", error);
                alert("An error occurred while saving location.");
            });
    };

    return (
        <div className="edit-location-container">
            {isLoading ? (
                <p>Loading...</p>
            ) : (
                <div className="edit-location-form">
                    <h2>Edit Location</h2>
                    <label>
                        Address:
                        <input
                            type="text"
                            value={address}
                            onChange={handleAddressChange}
                            placeholder="Enter new address"
                        />
                    </label>
                    <button className="update-coordinates-button" onClick={handleUpdateCoordinates}>
                        Update Coordinates
                    </button>
                    <button className="save-location-button" onClick={handleSaveLocation}>
                        Save Location
                    </button>
                    {error && <p className="error-message">{error}</p>}

                    {location.lat && location.lng && (
                        <div className="location-info">
                            <p></p>
                            <p></p>
                        </div>
                    )}

                    {location.lat && location.lng && (
                        <LoadScript googleMapsApiKey={API_KEY}>
                            <GoogleMap
                                center={{ lat: location.lat, lng: location.lng }}
                                zoom={15}
                                mapContainerStyle={{ width: "100%", height: "400px" }}
                            >
                                <Marker position={{ lat: location.lat, lng: location.lng }} />
                            </GoogleMap>
                        </LoadScript>
                    )}
                </div>
            )}
        </div>
    );
}

export default EditLocation;
