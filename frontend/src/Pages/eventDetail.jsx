import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { GoogleMap, LoadScript, Marker, DirectionsRenderer } from '@react-google-maps/api';
import '../CSS/EventDetail.css';

const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

const EventDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [eventDetail, setEventDetail] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [user, setUser] = useState(null);
    const [directions, setDirections] = useState(null);
    const token = localStorage.getItem('accessToken');

    useEffect(() => {
        fetch(`http://localhost:8081/event/events/${id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch event details');
                }
                return response.json();
            })
            .then((data) => {
                setEventDetail(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error('Error fetching event details:', err);
                setError(err.message || 'An error occurred while fetching event details');
                setLoading(false);
            });
    }, [id, token]);

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
                    setUser(data);
                    setLoading(false);
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                    setError(error.message || 'Failed to fetch user data');
                    setLoading(false);
                });
        } else {
            setError('No token found');
            setLoading(false);
        }
    }, [token]);

    const handleJoinEvent = () => {
        if (!user) return;

        const eventParticipant = {
            eventId: id,
            userId: user.userId,
        };

        fetch('http://localhost:8081/eventParticipant/eventParticipants', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(eventParticipant),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to join the event');
                }
                return response.json();
            })
            .then((result) => {
                if (result) {
                    alert('Successfully joined the event!');
                } else {
                    alert('Failed to join the event. You have time conflict or you already attended this event.');
                }
            })
            .catch((err) => {
                console.error('Error joining the event:', err);
                setError(err.message || 'Failed to join the event');
            });
    };


    const handleNavigateToMessages = () => {
        navigate(`/event/${id}/messages`);
    };

    const handleSetRoute = () => {
        if (user && eventDetail) {
            const { latitude: userLat, longitude: userLng } = user.location;
            const { latitude: eventLat, longitude: eventLng } = eventDetail.location;

            const origin = new window.google.maps.LatLng(userLat, userLng);
            const destination = new window.google.maps.LatLng(eventLat, eventLng);

            const directionsService = new window.google.maps.DirectionsService();
            directionsService.route(
                {
                    origin,
                    destination,
                    travelMode: window.google.maps.TravelMode.DRIVING,
                },
                (result, status) => {
                    if (status === window.google.maps.DirectionsStatus.OK) {
                        setDirections(result);
                    } else {
                        console.error('Error fetching directions:', result);
                        alert('Could not retrieve directions.');
                    }
                }
            );
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="event-detail-container">
            {eventDetail ? (
                <div className="event-detail-content">
                    <h1 className="event-title">{eventDetail.title}</h1>
                    <p className="event-description"><strong>Description:</strong> {eventDetail.description}</p>
                    <p className="event-time"><strong>Start Time:</strong> {new Date(eventDetail.startTime).toLocaleString()}</p>
                    <p className="event-time"><strong>End Time:</strong> {new Date(eventDetail.endTime).toLocaleString()}</p>
                    <p className="event-location"><strong>Location:</strong> {eventDetail.location.address}</p>
                    <p className="event-organizer"><strong>Organizer:</strong> {eventDetail.organizer.name} {eventDetail.organizer.surname}</p>

                    <div className="event-actions">
                        <button className="action-button" onClick={handleJoinEvent}>Join Event</button>
                        <button className="action-button" onClick={handleNavigateToMessages}>Message</button>
                        <button className="action-button" onClick={handleSetRoute}>Set Route</button>
                    </div>

                    <LoadScript googleMapsApiKey={API_KEY}>
                        <GoogleMap
                            center={{ lat: eventDetail.location.latitude, lng: eventDetail.location.longitude }}
                            zoom={15}
                            mapContainerStyle={{ width: "100%", height: "400px" }}
                        >
                            <Marker position={{ lat: eventDetail.location.latitude, lng: eventDetail.location.longitude }} />
                            {directions && (
                                <DirectionsRenderer directions={directions} />
                            )}
                        </GoogleMap>
                    </LoadScript>
                </div>
            ) : (
                <p>Event details not found</p>
            )}
        </div>
    );
};

export default EventDetail;
