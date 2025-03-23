import { useState } from "react";
import axios from "axios";

const SharePage = () => {
  const [activityId, setActivityId] = useState("");
  const [goalId, setGoalId] = useState("");
  const [recipientUsername, setRecipientUsername] = useState("");
  const [activityMessage, setActivityMessage] = useState("");
  const [goalMessage, setGoalMessage] = useState("");
  const [isActivityError, setIsActivityError] = useState(false);
  const [isGoalError, setIsGoalError] = useState(false);
  const [isActivityLoading, setIsActivityLoading] = useState(false);
  const [isGoalLoading, setIsGoalLoading] = useState(false);
  const token = localStorage.getItem("token");

  const handleActivityShare = async () => {
    if (!token) {
      setActivityMessage("No token found. Please log in.");
      setIsActivityError(true);
      return;
    }

    setIsActivityLoading(true);
    try {
      await axios.post(
        '${process.env.API_URL}/api/auth/share',
        { activityId, recipientUsername },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setActivityMessage("Activity shared successfully.");
      setIsActivityError(false);
    } catch (error) {
      console.error("Failed to share activity:", error);
      setActivityMessage(
        error.response?.data?.message || "Failed to share activity. Please try again later."
      );
      setIsActivityError(true);
    } finally {
      setIsActivityLoading(false);
    }
  };

  const handleGoalShare = async () => {
    if (!token) {
      setGoalMessage("No token found. Please log in.");
      setIsGoalError(true);
      return;
    }

    setIsGoalLoading(true);
    try {
      await axios.post(
        '${process.env.API_URL}/api/auth/share-goal',
        { goalId, recipientUsername },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setGoalMessage("Goal shared successfully.");
      setIsGoalError(false);
    } catch (error) {
      console.error("Failed to share goal:", error);
      setGoalMessage(
        error.response?.data?.message || "Failed to share goal. Please try again later."
      );
      setIsGoalError(true);
    } finally {
      setIsGoalLoading(false);
    }
  };

  if (!token) {
    return <div className="error">No token found. Please log in.</div>;
  }

  return (
    <div className="share-page">
      <h2>Share Activity or Goal</h2>

      {/* Recipient Username Input */}
      <div className="form-group">
        <label>Recipient Username:</label>
        <input
          type="text"
          value={recipientUsername}
          onChange={(e) => setRecipientUsername(e.target.value)}
          placeholder="Enter the recipient's username"
        />
      </div>

      {/* Share Activity Section */}
      <div className="share-section">
        <h3>Share Activity</h3>
        <div className="form-group">
          <label>Activity ID:</label>
          <input
            type="text"
            value={activityId}
            onChange={(e) => setActivityId(e.target.value)}
            placeholder="Enter the activity ID"
          />
        </div>
        <button
          onClick={handleActivityShare}
          disabled={isActivityLoading || !activityId || !recipientUsername}
        >
          {isActivityLoading ? "Sharing..." : "Share Activity"}
        </button>
        {activityMessage && (
          <div className={`message ${isActivityError ? "error" : "success"}`}>
            {activityMessage}
          </div>
        )}
      </div>

      {/* Share Goal Section */}
      <div className="share-section">
        <h3>Share Goal</h3>
        <div className="form-group">
          <label>Goal ID:</label>
          <input
            type="text"
            value={goalId}
            onChange={(e) => setGoalId(e.target.value)}
            placeholder="Enter the goal ID"
          />
        </div>
        <button
          onClick={handleGoalShare}
          disabled={isGoalLoading || !goalId || !recipientUsername}
        >
          {isGoalLoading ? "Sharing..." : "Share Goal"}
        </button>
        {goalMessage && (
          <div className={`message ${isGoalError ? "error" : "success"}`}>
            {goalMessage}
          </div>
        )}
      </div>
    </div>
  );
};

export default SharePage;