import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/dashboard.css';

const Dashboard = () => {
    const [events, setEvents] = useState([]);
    const [suggestedEvents, setSuggestedEvents] = useState([]);
    const token = localStorage.getItem('accessToken');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [userData, setUserData] = useState(null);

    useEffect(() => {
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
                    setUserData(data);
                    setLoading(false);
                    setError('');
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                    setError('Failed to fetch user data');
                    setLoading(false);
                });
        } else {
            setError('No token found');
            setLoading(false);
        }
    }, [token]);

    useEffect(() => {
        fetch('http://localhost:8081/event/events', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => setEvents(data))
            .catch(error => console.error('Error fetching events:', error));

        if (userData && userData.userId) {
            fetch(`http://localhost:8081/event/events/suggest/by-user/${userData.userId}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => setSuggestedEvents(data))
                .catch(error => console.error('Error fetching suggested events:', error));
        }
    }, [token, userData]);

    return (
        <div className="dashboard">
            <h1>Welcome to Your Dashboard</h1>
            <div className="link-container">
                <Link to="/profile">View Profile</Link>
                <Link to="/createEvent">Create Event</Link>
            </div>

            <div>
                <h1>Suggested Events</h1>
                {suggestedEvents.length > 0 ? (
                    <ul>
                        {suggestedEvents.map(event => (
                            <li key={event.eventId}>
                                <h2>{event.title}</h2>
                                <p>Description: {event.description}</p>
                                <p>Start Time: {new Date(event.startTime).toLocaleString()}</p>
                                <p>End Time: {new Date(event.endTime).toLocaleString()}</p>
                                <p>Location: {event.location.address}</p>
                                <p>Organizer: {event.organizer.name} {event.organizer.surname}</p>
                                <Link to={`/event/${event.eventId}`}>
                                    <button>View Event Details</button>
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No suggested events available</p>
                )}
            </div>

            <div>
                <h1>Events List</h1>
                {events.length > 0 ? (
                    <ul>
                        {events.map(event => (
                            <li key={event.eventId}>
                                <h2>{event.title}</h2>
                                <p>Description: {event.description}</p>
                                <p>Start Time: {new Date(event.startTime).toLocaleString()}</p>
                                <p>End Time: {new Date(event.endTime).toLocaleString()}</p>
                                <p>Location: {event.location.address}</p>
                                <p>Organizer: {event.organizer.name} {event.organizer.surname}</p>
                                <Link to={`/event/${event.eventId}`}>
                                    <button>View Event Details</button>
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No events available</p>
                )}
            </div>
        </div>
    );
};

export default Dashboard;
