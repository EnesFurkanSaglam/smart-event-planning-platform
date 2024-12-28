import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/profile.css';

const Profile = () => {
    const [userData, setUserData] = useState(null);
    const [createdEvents, setCreatedEvents] = useState([]);
    const [attendedEvents, setAttendedEvents] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');
    const [points, setPoints] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            fetch('http://localhost:8081/user/users/me', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            })
                .then((response) => response.json())
                .then((data) => {
                    setUserData(data);
                    setIsLoading(false);
                    setError('');

                    const userId = data.userId;
                    if (userId) {
                        fetch(`http://localhost:8081/event/events/by-user/${userId}`, {
                            method: 'GET',
                            headers: {
                                'Authorization': `Bearer ${token}`,
                                'Content-Type': 'application/json',
                            },
                        })
                            .then((response) => response.json())
                            .then((eventsData) => setAttendedEvents(eventsData))
                            .catch(() => setError('Failed to fetch events I will attend'));

                        fetch(`http://localhost:8081/event/events/by-organizer/${userId}`, {
                            method: 'GET',
                            headers: {
                                'Authorization': `Bearer ${token}`,
                                'Content-Type': 'application/json',
                            },
                        })
                            .then((response) => response.json())
                            .then((eventsData) => setCreatedEvents(eventsData))
                            .catch(() => setError('Failed to fetch created events'));

                        fetch(`http://localhost:8081/point/points/sum/${userId}`, {
                            method: 'GET',
                            headers: {
                                'Authorization': `Bearer ${token}`,
                                'Content-Type': 'application/json',
                            },
                        })
                            .then((response) => response.json())
                            .then((pointsData) => setPoints(pointsData))
                            .catch(() => setError('Failed to fetch points'));
                    } else {
                        setError('User ID is null');
                    }
                })
                .catch(() => {
                    setError('Failed to fetch user data');
                    setIsLoading(false);
                });
        } else {
            setError('No token found');
            setIsLoading(false);
        }
    }, []);

    const renderEventList = (events) => (
        <ul>
            {events.map((event) => (
                <li key={event.eventId}>
                    <div>
                        <strong>Title:</strong> {event.title}
                    </div>
                    <div>
                        <strong>Description:</strong> {event.description}
                    </div>
                    <div>
                        <strong>Start Time:</strong> {new Date(event.startTime).toLocaleString()}
                    </div>
                    <div>
                        <strong>End Time:</strong> {new Date(event.endTime).toLocaleString()}
                    </div>
                    <div>
                        <strong>Category:</strong> {event.category}
                    </div>
                    <Link to={`/event/${event.eventId}`} className="event-detail-link">
                        <button>View Event Details</button>
                    </Link>
                </li>
            ))}
        </ul>
    );

    return (
        <div className="profile-container">
            <h2>My Profile</h2>

            <Link to="/edit-profile">Edit Profile</Link>

            <div className="events-section">

                <h2>Events I Created</h2>
                {isLoading ? (
                    <p className="loading-indicator">Loading...</p>
                ) : error ? (
                    <p className="error-message">{error}</p>
                ) : createdEvents.length > 0 ? (
                    renderEventList(createdEvents)
                ) : (
                    <p>No events found</p>
                )}
            </div>

            <div className="events-section">
                <h2>Events I Will Attend</h2>
                {isLoading ? (
                    <p className="loading-indicator">Loading...</p>
                ) : error ? (
                    <p className="error-message">{error}</p>
                ) : attendedEvents.length > 0 ? (
                    renderEventList(attendedEvents)
                ) : (
                    <p>No events found</p>
                )}
            </div>

            <div className="points">
                My Points: {points !== null ? points : 'Loading points...'}
            </div>

        </div>
    );
};

export default Profile;
