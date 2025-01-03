import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../CSS/LoginPage.css';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8081/authenticate', {
                username: username,
                password: password
            });

            if (response.data) {
                const { accessToken } = response.data.payload;
                localStorage.setItem('accessToken', accessToken);
                navigate('/dashboard');
            }
        } catch (error) {
            console.error('Error during login:', error);
            setError('Invalid username or password');
        }
    };

    const handleCreateAccount = () => {
        console.log('New User Create');
        navigate('/register');
    };

    const handleForgotPassword = () => {
        console.log('Forgot password');
        navigate('/forgot-password');
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <div className="input-container">
                <label>Username</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Type Username"
                    className="input-field"
                />
            </div>
            <div className="input-container">
                <label>Password</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Type Password"
                    className="input-field"
                />
            </div>
            <button onClick={handleLogin} className="login-button">
                Login
            </button>
            {error && <div className="error-message">{error}</div>}
            <div className="link-container">
                <button onClick={handleCreateAccount} className="link-button">
                    Create new User
                </button>
                <button onClick={handleForgotPassword} className="link-button">
                    Forgot password
                </button>
            </div>
        </div>
    );
};

export default LoginPage;
