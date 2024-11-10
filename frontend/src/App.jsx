import './App.css'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import axios from 'axios';

import LoginPage from './Pages/loginPage'
import Dashboard from './Pages/dashboard'
import Register from './Pages/register'
import Profile from './Pages/profile'
import EditProfile from './Pages/editProfile'

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/edit-profile" element={<EditProfile />} />
        {/* Add more routes here */}
      </Routes>
    </Router>
  )
}

export default App
