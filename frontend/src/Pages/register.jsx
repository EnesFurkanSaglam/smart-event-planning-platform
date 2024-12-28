import React, { useState } from 'react';
import axios from 'axios';
import '../CSS/Register.css';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/register', {
                username,
                email,
                password,
            });
            console.log('User registered successfully', response.data);
            alert('User registered successfully',)
            navigate('/');

        } catch (err) {
            console.error('Registration error', err);
            setError('An error occurred during registration');
        }
    };

    return (
        <div className="register-container">
            <h2 className="register-title">Register</h2>
            <form onSubmit={handleRegister}>
                <div className="register-form-group">
                    <label className="register-label" htmlFor="username">Username</label>
                    <input
                        className="register-input"
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="register-form-group">
                    <label className="register-label" htmlFor="email">Email</label>
                    <input
                        className="register-input"
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="register-form-group">
                    <label className="register-label" htmlFor="password">Password</label>
                    <input
                        className="register-input"
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className="register-error">{error}</p>}
                <button className="register-button" type="submit">Register</button>
            </form>
        </div>
    );
};

export default Register;
