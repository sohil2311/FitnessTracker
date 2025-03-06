import { useState } from "react";

const EditGoalForm = ({ goal, onSave, onCancel }) => {
  const [formData, setFormData] = useState({
    description: goal.description,
    target: goal.target,
    targetType: goal.targetType,
    startDate: goal.startDate,
    endDate: goal.endDate,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({ ...goal, ...formData }); 
  };

  return (
    <div className="edit-form">
      <h3>Edit Goal</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="description"
          placeholder="Description"
          value={formData.description}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="target"
          placeholder="Target"
          value={formData.target}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="targetType"
          placeholder="Target Type"
          value={formData.targetType}
          onChange={handleChange}
          required
        />
        <input
          type="date"
          name="startDate"
          placeholder="Start Date"
          value={formData.startDate}
          onChange={handleChange}
          required
        />
        <input
          type="date"
          name="endDate"
          placeholder="End Date"
          value={formData.endDate}
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

export default EditGoalForm;