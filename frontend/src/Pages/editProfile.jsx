import React, { useState, useEffect } from 'react';

const EditProfile = () => {

    const [userData, setUserData] = useState({
        username: '',
        email: '',
        gender: '',
        name: '',
        phone: '',
        profile_picture: '',
        surname: '',
        interests: ''
    });
    const [isLoading, setIsLoading] = useState(true); // To handle loading state
    const [error, setError] = useState(null); // For handling errors

    useEffect(() => {
        // Get token from localStorage
        const token = localStorage.getItem('accessToken');

        if (token) {
            // Decode token to get the username (or handle it according to your JWT structure)
            const username = getUsernameFromToken(token);  // Token'dan kullanıcı adını alıyoruz

            fetch(`http://localhost:8081/user/users/me`, { // Kullanıcı adını URL'ye ekliyoruz
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
                })
                .catch((error) => {
                    setError('Failed to fetch user data');
                    setIsLoading(false);
                });
        } else {
            setError('No token found');
            setIsLoading(false);
        }
    }, []);

    const getUsernameFromToken = (token) => {
        // Token'dan kullanıcı adını almak için basit bir fonksiyon
        // Eğer JWT'nizin payload kısmında `sub` varsa, bunu kullanabilirsiniz
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64)
            .split('')
            .map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join(''));
        const parsedData = JSON.parse(jsonPayload);
        return parsedData.sub;  // 'sub' JWT'deki kullanıcı adı olabilir
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserData({
            ...userData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Submit logic (e.g., API call)
        console.log('User data submitted:', userData);
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="user-profile-edit">
            <h2>Edit User Profile</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={userData.username}
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
                    <label htmlFor="gender">Gender:</label>
                    <select
                        id="gender"
                        name="gender"
                        value={userData.gender}
                        onChange={handleInputChange}
                        required
                    >
                        <option value="">Select gender</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                        <option value="other">Other</option>
                    </select>
                </div>

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
                    <label htmlFor="profile_picture">Profile Picture URL:</label>
                    <input
                        type="url"
                        id="profile_picture"
                        name="profile_picture"
                        value={userData.profile_picture}
                        onChange={handleInputChange}
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
                    <label htmlFor="interests">Interests:</label>
                    <textarea
                        id="interests"
                        name="interests"
                        value={userData.interests}
                        onChange={handleInputChange}
                    />
                </div>

                <button type="submit">Save Changes</button>
            </form>
        </div>
    );
};

export default EditProfile;
