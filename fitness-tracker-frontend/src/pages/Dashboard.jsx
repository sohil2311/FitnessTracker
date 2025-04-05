import { useEffect, useState } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import ErrorBoundary from "../components/ErrorBoundary";
import ActivityList from "../components/ActivityList";
import GoalList from "../components/GoalList";
import AddActivity from "../components/AddActivity";
import AddGoal from "../components/AddGoal";
import EditActivityForm from "../components/EditActivityForm";
import EditGoalForm from "../components/EditGoalForm";

const Dashboard = () => {
  const [activities, setActivities] = useState([]);
  const [goals, setGoals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isAdmin, setIsAdmin] = useState(false);
  const [editingActivity, setEditingActivity] = useState(null); 
  const [editingGoal, setEditingGoal] = useState(null); 
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      console.error("No token found. Please log in.");
      setError("No token found. Please log in.");
      setLoading(false);
      return;
    }

    
    const decodedToken = jwtDecode(token);
    const adminCheck = decodedToken.role === "ROLE_ADMIN"; 
    setIsAdmin(adminCheck); 

    const fetchData = async () => {
      try {
        const headers = { Authorization: `Bearer ${token}` };

        
        if (adminCheck) {
          
          const [activitiesResponse, goalsResponse] = await Promise.all([
            axios.get(`${process.env.REACT_APP_API_URL}/api/activities`, { headers }),
            axios.get(`${process.env.REACT_APP_API_URL}/api/goals`, { headers }),
          ]);
          setActivities(activitiesResponse.data);
          setGoals(goalsResponse.data);
        } else {
          
          const [activitiesResponse, goalsResponse] = await Promise.all([
            axios.get(`${process.env.REACT_APP_API_URL}/api/activities/user`, { headers }),
            axios.get(`${process.env.REACT_APP_API_URL}/api/goals/user`, { headers }),
          ]);
          setActivities(activitiesResponse.data);
          setGoals(goalsResponse.data);
        }
      } catch (error) {
        console.error("Failed to fetch data:", error);
        setError("Failed to fetch data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchData(); 
  }, [token]); 

  
  const handleEditActivity = (activity) => {
    setEditingActivity(activity); 
  };

  
  const handleSaveActivity = async (updatedActivity) => {
    try {
      const headers = { Authorization: `Bearer ${token}` };
      await axios.put(
        `${process.env.REACT_APP_API_URL}/api/activities/edit/${updatedActivity.id}`,
        updatedActivity,
        { headers }
      );
      setActivities(
        activities.map((activity) =>
          activity.id === updatedActivity.id ? updatedActivity : activity
        )
      );
      setEditingActivity(null); 
    } catch (error) {
      console.error("Failed to update activity:", error);
    }
  };

  
  const handleDeleteActivity = async (id) => {
    try {
      await axios.delete(`${process.env.REACT_APP_API_URL}/api/activities/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setActivities(activities.filter((activity) => activity.id !== id)); 
    } catch (error) {
      console.error("Failed to delete activity:", error);
    }
  };

  
  const handleEditGoal = (goal) => {
    setEditingGoal(goal); 
  };

  
  const handleSaveGoal = async (updatedGoal) => {
    try {
      const headers = { Authorization: `Bearer ${token}` };
      await axios.put(
        `${process.env.REACT_APP_API_URL}/api/goals/edit/${updatedGoal.id}`,
        updatedGoal,
        { headers }
      );
      setGoals(
        goals.map((goal) =>
          goal.id === updatedGoal.id ? updatedGoal : goal
        )
      );
      setEditingGoal(null); 
    } catch (error) {
      console.error("Failed to update goal:", error);
    }
  };

  
  const handleDeleteGoal = async (id) => {
    try {
      await axios.delete(`${process.env.REACT_APP_API_URL}/api/goals/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setGoals(goals.filter((goal) => goal.id !== id)); 
    } catch (error) {
      console.error("Failed to delete goal:", error);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <ErrorBoundary>
      <div className="dashboard">
        <h2>Dashboard</h2>

        {/* Show Add Activity and Add Goal forms only for regular users */}
        {!isAdmin && (
          <div className="forms">
            <div className="addActivity">
              <AddActivity />
            </div>
            <div className="addGoal">
              <AddGoal />
            </div>
          </div>
        )}

        {/* Show Activity List and Goal List for all users */}
        <div className="activities">
          {editingActivity ? (
            <EditActivityForm
              activity={editingActivity}
              onSave={handleSaveActivity}
              onCancel={() => setEditingActivity(null)}
            />
          ) : (
            <ActivityList
              activities={activities}
              onEdit={handleEditActivity}
              onDelete={handleDeleteActivity}
            />
          )}
        </div>
        <div className="goals">
          {editingGoal ? (
            <EditGoalForm
              goal={editingGoal}
              onSave={handleSaveGoal}
              onCancel={() => setEditingGoal(null)}
            />
          ) : (
            <GoalList
              goals={goals}
              onEdit={handleEditGoal}
              onDelete={handleDeleteGoal}
            />
          )}
        </div>
      </div>
    </ErrorBoundary>
  );
};

export default Dashboard;