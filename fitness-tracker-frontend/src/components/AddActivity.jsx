import { useState } from "react";
import axios from "axios";

const AddActivity = () => {
  const [type, setType] = useState("");
  const [duration, setDuration] = useState("");
  const [caloriesBurned, setCaloriesBurned] = useState("");
  const [date, setDate] = useState("");
  const token = localStorage.getItem("token");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        `${import.meta.env.VITE_API_URL}/api/activities/add`,
        { type, duration, caloriesBurned, date },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Activity added successfully!");
    } catch (error) {
      console.error("Failed to add activity:", error);
    }
  };

  return (
    <div>
      <h3>Add Activity</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Type"
          value={type}
          onChange={(e) => setType(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Duration (in minutes)"
          value={duration}
          onChange={(e) => setDuration(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Calories Burned"
          value={caloriesBurned}
          onChange={(e) => setCaloriesBurned(e.target.value)}
          required
        />
        <input
          type="date"
          placeholder="Date"
          value={date}
          onChange={(e) => setDate(e.target.value)}
          required
        />
        <button type="submit">Add Activity</button>
      </form>
    </div>
  );
};

export default AddActivity;