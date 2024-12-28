import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
import axios from "axios";
import '../CSS/createEvent.css';

const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;


async function getCoordsForAddress(address) {
    try {
        const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
                address
            )}&key=${API_KEY}`
        );
        const data = response.data;

        if (
            !data ||
            data.status === "ZERO_RESULTS" ||
            !Array.isArray(data.results) ||
            data.results.length === 0
        ) {
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

const CreateEvent = () => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [locationId, setLocationId] = useState('');
    const [organizerId, setOrganizerId] = useState('');
    const [category, setCategory] = useState('');
    const [address, setAddress] = useState('');
    const [location, setLocation] = useState({ lat: null, lng: null });
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            fetch(`http://localhost:8081/user/users/me`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    setOrganizerId(data.userId);
                    setLocationId(data.locationId);
                    setIsLoading(false);
                    setError('');
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                    setError('Failed to fetch user data');
                    setIsLoading(false);
                });
        } else {
            setError('No token found');
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

    const handleSubmit = async (e) => {
        e.preventDefault();
        const accessToken = localStorage.getItem('accessToken');
        if (!accessToken) {
            alert('Access token is missing. Please log in.');
            return;
        }

        const event = {
            title,
            description,
            startTime,
            endTime,
            location: {
                locationId: null,
                latitude: location.lat,
                longitude: location.lng,
                address
            },
            organizerId,
            category
        };

        try {
            const response = await fetch('http://localhost:8081/event/events', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}`
                },
                body: JSON.stringify(event)
            });

            if (response.ok) {
                alert('Event Created Successfully!');
                navigate('/dashboard');
            } else {
                const errorData = await response.json();
                alert(`Error: ${errorData.message || 'Failed to create event'}`);
            }
        } catch (error) {
            console.error('Error creating event:', error);
            alert('An error occurred while creating the event.');
        }
    };

    return (
        <div className="create-event">
            <h2>Create Event</h2>
            {isLoading ? (
                <p>Loading...</p>
            ) : error ? (
                <p>{error}</p>
            ) : (
                <form onSubmit={handleSubmit}>
                    <input type="text" placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} required />
                    <textarea placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} />
                    <input type="datetime-local" placeholder="Start Time" value={startTime} onChange={(e) => setStartTime(e.target.value)} required />
                    <input type="datetime-local" placeholder="End Time" value={endTime} onChange={(e) => setEndTime(e.target.value)} required />
                    <input type="text" placeholder="Category" value={category} onChange={(e) => setCategory(e.target.value)} />

                    <input type="text" placeholder="Address" value={address} onChange={handleAddressChange} required />
                    <button type="button" onClick={handleUpdateCoordinates}>Update Coordinates</button>

                    {location.lat && location.lng && (
                        <div className="map-container">
                            <LoadScript googleMapsApiKey={API_KEY}>
                                <GoogleMap
                                    center={{ lat: location.lat, lng: location.lng }}
                                    zoom={15}
                                    mapContainerStyle={{ width: "100%", height: "400px" }}
                                >
                                    <Marker position={{ lat: location.lat, lng: location.lng }} />
                                </GoogleMap>
                            </LoadScript>
                        </div>
                    )}

                    <button type="submit">Create Event</button>
                </form>
            )}
        </div>
    );
};

export default CreateEvent;
