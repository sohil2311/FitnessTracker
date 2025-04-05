import { useEffect, useState } from "react";
import axios from "axios";

const SharedGoalsPage = () => {
  const [sharedGoals, setSharedGoals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      setError("No token found. Please log in.");
      setLoading(false);
      return;
    }

    const fetchSharedGoals = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_URL}/api/auth/shared-goals`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log("Shared Goals Data:", response.data); // Log the data
        setSharedGoals(response.data);
      } catch (error) {
        console.error("Failed to fetch shared goals:", error);
        setError("Failed to fetch shared goals. Please try again later.");
      } finally {
        setLoading(false);
      }
    };
    fetchSharedGoals();
  }, [token]);

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${process.env.REACT_APP_API_URL}/api/auth/shared-goals/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      // Remove the deleted goal from the list
      setSharedGoals((prevGoals) => prevGoals.filter((goal) => goal.id !== id));
    } catch (error) {
      console.error("Failed to delete shared goal:", error);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="shared-goals-page">
      <h2>Shared Goals</h2>
      {sharedGoals.length === 0 ? (
        <p>No shared goals found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>From</th>
              <th>Goal</th>
              <th>Target</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Shared At</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sharedGoals.map((sharedGoal) => (
              <tr key={sharedGoal.id}>
                <td>{sharedGoal.senderUsername}</td>
                <td>{sharedGoal.goalDescription}</td>
                <td>{sharedGoal.goalTarget}</td>
                <td>{new Date(sharedGoal.goalStartDate).toLocaleDateString()}</td>
                <td>{new Date(sharedGoal.goalEndDate).toLocaleDateString()}</td>
                <td>{new Date(sharedGoal.sharedAt).toLocaleString()}</td>
                <td>
                  <button
                    className="delete-button"
                    onClick={() => handleDelete(sharedGoal.id)}
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

export default SharedGoalsPage;