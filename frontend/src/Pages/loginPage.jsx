import React, { useState } from 'react';
import '../CSS/LoginPage.css'

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        console.log('Giriş yapılıyor:', username, password);
    };

    const handleCreateAccount = () => {
        console.log('Yeni kullanıcı oluştur');
    };

    const handleForgotPassword = () => {
        console.log('Şifremi unuttum');
    };

    return (
        <div className="container">
            <h2>Giriş Yap</h2>
            <div className="input-container">
                <label>Kullanıcı Adı</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Kullanıcı Adınızı Girin"
                />
            </div>
            <div className="input-container">
                <label>Şifre</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Şifrenizi Girin"
                />
            </div>
            <button onClick={handleLogin} className="button">
                Giriş Yap
            </button>
            <div className="link-container">
                <button onClick={handleCreateAccount} className="link-button">
                    Yeni Kullanıcı Oluştur
                </button>
                <button onClick={handleForgotPassword} className="link-button">
                    Şifremi Unuttum
                </button>
            </div>
        </div>
    );
};

export default LoginPage;
