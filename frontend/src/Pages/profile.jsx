import React from 'react';
import { Link } from 'react-router-dom';

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
