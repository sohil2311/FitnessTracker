import { useState } from "react";
import axios from "axios";

const AddGoal = () => {
  const [description, setDescription] = useState("");
  const [target, setTarget] = useState("");
  const [targetType, setTargetType] = useState(""); 
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const token = localStorage.getItem("token");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "http://localhost:8080/api/goals/add",
        { description, target, targetType, startDate, endDate },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Goal added successfully!");
    } catch (error) {
      console.error("Failed to add goal:", error);
    }
  };

  return (
    <div>
      <h3>Add Goal</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Target"
          value={target}
          onChange={(e) => setTarget(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Target Type"
          value={targetType} 
          onChange={(e) => setTargetType(e.target.value)} 
          required
        />
        <input
          type="date"
          placeholder="Start Date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
          required
        />
        <input
          type="date"
          placeholder="End Date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
          required
        />
        <button type="submit">Add Goal</button>
      </form>
    </div>
  );
};

export default AddGoal;