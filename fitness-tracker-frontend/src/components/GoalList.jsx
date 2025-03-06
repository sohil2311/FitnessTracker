const GoalList = ({ goals, onEdit, onDelete }) => {
  return (
    <div className="goal-list">
      <h3>Goals</h3>
      {goals.length === 0 ? (
        <p>No goals found.</p>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <table>
            <thead>
              <tr>
                <th>Id</th>
                <th>User</th>
                <th>Description</th>
                <th>Target</th>
                <th>Target Type</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {goals.map((goal) => (
                <tr key={goal.id}>
                  <td>{goal.id}</td>
                  <td>{goal.username}</td>
                  <td>{goal.description}</td>
                  <td>{goal.target}</td>
                  <td>{goal.targetType}</td>
                  <td>{new Date(goal.startDate).toLocaleDateString()}</td>
                  <td>{new Date(goal.endDate).toLocaleDateString()}</td>
                  <td>
                    <div className="button-container">
                      <button
                        className="edit-button"
                        onClick={() => onEdit(goal)}
                      >
                        Edit
                      </button>
                      <button
                        className="delete-button"
                        onClick={() => onDelete(goal.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default GoalList;