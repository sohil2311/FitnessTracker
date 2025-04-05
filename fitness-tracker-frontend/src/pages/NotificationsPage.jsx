import { useEffect, useState } from "react";
import axios from "axios";

const NotificationsPage = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await axios.get('${process.env.API_URL}/api/auth/notifications', {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log("API Response:", response);
        setNotifications(response.data);
      } catch (error) {
        console.error("Failed to fetch notifications:", error);
        setError("Failed to fetch notifications. Please try again later.");
      } finally {
        setLoading(false);
      }
    };
    fetchNotifications();
  }, [token]);

  const handleDeleteNotification = async (id) => {
    try {
      await axios.delete(`${process.env.REACT_APP_API_URL}/api/auth/notifications/${id}`
, {
        headers: { Authorization: `Bearer ${token}` },
      });
      
      setNotifications(notifications.filter((notification) => notification.id !== id));
    } catch (error) {
      console.error("Failed to delete notification:", error);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="notifications-page">
      <h2>Notifications</h2>
      {notifications.length === 0 ? (
        <p>No notifications found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Message</th>
              <th>Created At</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {notifications.map((notification) => (
              <tr key={notification.id}>
                <td>{notification.message}</td>
                <td>{new Date(notification.createdAt).toLocaleString()}</td>
                <td>
                  <button
                    className="delete-button"
                    onClick={() => handleDeleteNotification(notification.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default NotificationsPage;