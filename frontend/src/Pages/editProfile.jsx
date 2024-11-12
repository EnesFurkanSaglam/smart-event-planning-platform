import React, { useState, useEffect } from 'react';

const EditProfile = () => {
    const [userData, setUserData] = useState({
        username: '',
        email: '',
        name: '',
        surname: '',
        phone: '',
        gender: '',
        interest: '',
        created_at: '',
        location: '',
        userId: '',
        password: '',
        profilePicture: ''
    });
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

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
                    console.log('User data fetched:', data);
                    setUserData(data);
                    setIsLoading(false);
                    setError(''); // Hata sıfırlanıyor
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

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserData({
            ...userData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const token = localStorage.getItem('accessToken');
        if (!token) {
            setError('No token found');
            return;
        }

        setSuccessMessage("");  // Her form gönderimi öncesinde başarı mesajını sıfırla
        fetch('http://localhost:8081/user/users', {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // Yanıtın içeriğini kontrol et
                return response.text().then((text) => {
                    try {
                        // Eğer yanıt JSON ise, JSON'a dönüştür
                        const data = JSON.parse(text);
                        return data;
                    } catch (e) {
                        // JSON parse hatası olursa yanıtı logla
                        console.error('Response is not valid JSON:', text);
                        throw new Error('Response is not in valid JSON format');
                    }
                });
            })
            .then((data) => {
                console.log('Update response:', data);
                if (data.success) {
                    setSuccessMessage('Profile updated successfully!');
                } else {
                    setError(`Failed to update profile: ${data.message || 'Unknown error'}`);
                }
            })
            .catch((error) => {
                console.error('Error while updating profile:', error);
                setError(`Error while updating profile: ${error.message}`);
            });
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
            {successMessage && <div style={{ color: 'green' }}>{successMessage}</div>}
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
                    <label htmlFor="gender">Gender:</label>
                    <select
                        id="gender"
                        name="gender"
                        value={userData.gender}
                        onChange={handleInputChange}
                        required
                    >
                        <option value="">Select gender</option>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                        <option value="OTHER">Other</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="interest">Interests:</label>
                    <textarea
                        id="interest"
                        name="interest"
                        value={userData.interest}
                        onChange={handleInputChange}
                    />
                </div>

                Profil fotoğrafı eklenmesi
                <div>
                    <label htmlFor="profilePicture">Profile Picture:</label>
                    <input
                        type="file"
                        id="profilePicture"
                        name="profilePicture"
                        onChange={handleInputChange}
                    />
                </div>

                <button type="submit">Save Changes</button>
            </form>
        </div>
    );
};

export default EditProfile;
