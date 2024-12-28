import React from 'react';
import { Link } from 'react-router-dom';
import '../CSS/EventList.css';

const EventList = ({ events, title }) => {
    return (
        <div className="event-list-container">
            <h2>{title}</h2>
            {events.length > 0 ? (
                <ul className="event-list">
                    {events.map((event) => (
                        <li key={event.eventId} className="event-item">
                            <div className="event-details">
                                <strong>Title:</strong> {event.title}
                            </div>
                            <div className="event-details">
                                <strong>Description:</strong> {event.description}
                            </div>
                            <div className="event-details">
                                <strong>Start Time:</strong> {new Date(event.startTime).toLocaleString()}
                            </div>
                            <div className="event-details">
                                <strong>End Time:</strong> {new Date(event.endTime).toLocaleString()}
                            </div>
                            <div className="event-details">
                                <strong>Category:</strong> {event.category}
                            </div>
                            <Link to={`/event/${event.eventId}`}>
                                <button className="view-event-button">View Event Details</button>
                            </Link>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No events found</p>
            )}
        </div>
    );
};

export default EventList;
