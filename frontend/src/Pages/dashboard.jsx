import React from 'react';
import { Link } from 'react-router-dom'; // Link component for navigation

const Dashboard = () => {
    return (
        <div className="dashboard-container">
            <h1>Welcome to Your Dashboard</h1>
            <p>This is your personal space where you can view and manage your account.</p>

            <div className="dashboard-actions">
                <Link to="/profile" className="dashboard-link">
                    View Profile
                </Link>
                <Link to="/settings" className="dashboard-link">
                    Account Settings
                </Link>
                <Link to="/logout" className="dashboard-link">
                    Logout
                </Link>
            </div>
        </div>
    );
};

export default Dashboard;
