import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import axios from 'axios';

import LoginPage from './Pages/loginPage';
import Dashboard from './Pages/dashboard';
import Register from './Pages/register';
import Profile from './Pages/profile';
import EditProfile from './Pages/editProfile';
import EventDetail from './Pages/eventDetail';
import CreateEvet from './Pages/createEvent';
import Message from './Pages/message';
import ForgotPassword from './Pages/forgotPassword';
import EditLocation from './Pages/editLocation';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/edit-profile" element={<EditProfile />} />
        <Route path="/event/:id" element={<EventDetail />} />
        <Route path="/createEvent" element={<CreateEvet />} />
        <Route path="/event/:id/messages" element={<Message />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/edit-location" element={<EditLocation />} />


        {/* Add more routes here */}
      </Routes>
    </Router>
  );
}

export default App;
