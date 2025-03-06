import { useState } from "react";

const EditActivityForm = ({ activity, onSave, onCancel }) => {
  const [formData, setFormData] = useState({
    type: activity.type,
    duration: activity.duration,
    caloriesBurned: activity.caloriesBurned,
    date: activity.date,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({ ...activity, ...formData }); 
  };

  return (
    <div className="edit-form">
      <h3>Edit Activity</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="type"
          placeholder="Type"
          value={formData.type}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="duration"
          placeholder="Duration (in minutes)"
          value={formData.duration}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="caloriesBurned"
          placeholder="Calories Burned"
          value={formData.caloriesBurned}
          onChange={handleChange}
          required
        />
        <input
          type="date"
          name="date"
          placeholder="Date"
          value={formData.date}
          onChange={handleChange}
          required
        />
        <button type="submit">Save</button>
        <button type="button" onClick={onCancel}>
          Cancel
        </button>
      </form>
    </div>
  );
};

export default EditActivityForm;