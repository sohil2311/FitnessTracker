import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { isAuthenticated } from "../utils/auth";

const ProfilePage = () => {
  const [email, setEmail] = useState("");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [loading, setLoading] = useState(true); 
  const [fetchError, setFetchError] = useState(false); 
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated()) {
      navigate("/login"); 
    } else {
      const fetchProfile = async () => {
        try {
          const response = await axios.get('${import.meta.env.VITE_API_URL}/api/auth/profile', {
            headers: { Authorization: `Bearer ${token}` },
          });
          setEmail(response.data.email); 
          setFetchError(false);
        } catch (error) {
          console.error("Failed to fetch profile:", error);
          setFetchError(true);
        } finally {
          setLoading(false); 
        }
      };
      fetchProfile();
    }
  }, [token, navigate]);

  const handleUpdateProfile = async () => {
    try {
      await axios.put(
        '${import.meta.env.VITE_API_URL}/api/auth/update-profile',
        { email },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMessage("Profile updated successfully.");
      setIsError(false);
    } catch (error) {
      setMessage("Failed to update profile.");
      setIsError(true);
    }
  };

  const handleUpdatePassword = async () => {
    try {
      await axios.put(
        `${process.env.REACT_APP_API_URL}/api/auth/update-password`
,
        { currentPassword, newPassword },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMessage("Password updated successfully.");
      setIsError(false);
    } catch (error) {
      setMessage("Failed to update password.");
      setIsError(true);
    }
  };

  if (loading) {
    return (
      <div className="profile-page">
        <h2>Profile</h2>
        <p>Loading profile...</p>
      </div>
    );
  }

  if (fetchError) {
    return (
      <div className="profile-page">
        <h2>Profile</h2>
        <p className="error">Failed to fetch profile. Please try again later.</p>
      </div>
    );
  }

  return (
    <div className="profile-page">
      <h2>Profile</h2>
      <div>
        <label>Email:</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          placeholder="Enter your new email"
        />
        <button onClick={handleUpdateProfile}>Update Email</button>
      </div>
      <br />
      <div>
        <label>Current Password:</label>
        <input
          type="password"
          value={currentPassword}
          onChange={(e) => setCurrentPassword(e.target.value)}
          required
          placeholder="Enter your current password"
        />
        <label>New Password:</label>
        <input
          type="password"
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
          required
          placeholder="Enter your new password"
        />
        <button onClick={handleUpdatePassword}>Update Password</button>
      </div>
      {message && (
        <div className={`message ${isError ? "error" : "success"}`}>
          {message}
        </div>
      )}
    </div>
  );
};

export default ProfilePage;