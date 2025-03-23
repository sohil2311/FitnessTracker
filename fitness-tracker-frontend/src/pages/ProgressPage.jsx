import { useEffect, useState } from "react";
import axios from "axios";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";


ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const ProgressPage = () => {
  const [progressData, setProgressData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      setError("No token found. Please log in.");
      setLoading(false);
      return;
    }

    const fetchProgress = async () => {
      try {
        const response = await axios.get('${process.env.API_URL}/api/goals/progress', {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log("Progress Data:", response.data); 
        setProgressData(response.data);
      } catch (error) {
        console.error("Failed to fetch progress data:", error);
        setError("Failed to fetch data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchProgress();
  }, [token]);

  const data = {
    labels: progressData.map((goalProgress) => goalProgress.goal.description), 
    datasets: [
      {
        label: "Progress",
        data: progressData.map((goalProgress) => goalProgress.progress), 
        backgroundColor: "rgba(75, 192, 192, 0.2)",
        borderColor: "rgba(75, 192, 192, 1)",
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true,
        max: 1, 
        ticks: {
          callback: (value) => `${(value * 100).toFixed(0)}%`, 
        },
      },
    },
    plugins: {
      tooltip: {
        callbacks: {
          label: (context) => {
            const label = context.dataset.label || "";
            const value = context.raw || 0;
            return `${label}: ${(value * 100).toFixed(2)}%`; 
          },
        },
      },
    },
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="progress-page">
      <h2>Progress Tracking</h2>
      <div className="chart-container">
        <Bar data={data} options={options} />
      </div>
    </div>
  );
};

export default ProgressPage;