import { useEffect, useState } from "react";
import axios from "axios";

const SharedActivitiesPage = () => {
  const [sharedActivities, setSharedActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      setError("No token found. Please log in.");
      setLoading(false);
      return;
    }

    const fetchSharedActivities = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/auth/shared-activities", {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log("Shared Activities Data:", response.data); 
        setSharedActivities(response.data);
      } catch (error) {
        console.error("Failed to fetch shared activities:", error);
        setError("Failed to fetch shared activities. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchSharedActivities();
  }, [token]);

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/auth/shared-activities/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

     
      setSharedActivities((prevActivities) =>
        prevActivities.filter((activity) => activity.id !== id)
      );
    } catch (error) {
      console.error("Failed to delete shared activity:", error);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="shared-activities-page">
      <h2>Shared Activities</h2>
      {sharedActivities.length === 0 ? (
        <p>No shared activities found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>From</th>
              <th>Activity</th>
              <th>Duration (mins)</th>
              <th>Calories Burned</th>
              <th>Date</th>
              <th>Shared At</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sharedActivities.map((sharedActivity) => (
              <tr key={sharedActivity.id}>
                <td>{sharedActivity.senderUsername}</td>
                <td>{sharedActivity.activityType}</td>
                <td>{sharedActivity.duration}</td>
                <td>{sharedActivity.caloriesBurned}</td>
                <td>{new Date(sharedActivity.activityDate).toLocaleDateString()}</td>
                <td>{new Date(sharedActivity.sharedAt).toLocaleString()}</td>
                <td>
                  <button
                    className="delete-button"
                    onClick={() => handleDelete(sharedActivity.id)}
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

export default SharedActivitiesPage;