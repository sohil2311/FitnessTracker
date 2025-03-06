const ActivityList = ({ activities, onEdit, onDelete }) => {
  return (
    <div className="activity-list">
      <h3>Activities</h3>
      {activities.length === 0 ? (
        <p>No activities found.</p>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <table>
            <thead>
              <tr>
                <th>Id</th>
                <th>User</th>
                <th>Type</th>
                <th>Duration (mins)</th>
                <th>Calories Burned</th>
                <th>Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {activities.map((activity) => (
                <tr key={activity.id}>
                  <td>{activity.id}</td>
                  <td>{activity.username}</td>
                  <td>{activity.type}</td>
                  <td>{activity.duration}</td>
                  <td>{activity.caloriesBurned}</td>
                  <td>{new Date(activity.date).toLocaleDateString()}</td>
                  <td>
                    <div className="button-container">
                      <button
                        className="edit-button"
                        onClick={() => onEdit(activity)}
                      >
                        Edit
                      </button>
                      <button
                        className="delete-button"
                        onClick={() => onDelete(activity.id)}
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

export default ActivityList;