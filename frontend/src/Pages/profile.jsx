import React from 'react';
import { Link } from 'react-router-dom'; // Link component for navigation

const Profile = () => {
    return (
        <>

            <div>
                <Link to="/edit-profile">
                    Edit Profile
                </Link>

            </div>

        </>
    );
};

export default Profile;
