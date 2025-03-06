import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Navbar from "./components/Navbar";
import ProfilePage from "./pages/ProfilePage";
import ProgressPage from "./pages/ProgressPage";
import SharePage from "./pages/SharePage";
import NotificationsPage  from "./pages/NotificationsPage";
import SharedActivitiesPage  from "./pages/SharedActivitiesPage";
import SharedGoalsPage from "./pages/SharedGoalsPage";


function App() {
  return (
    <div className="app">
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/progress" element={<ProgressPage />} />
        <Route path="/share" element={<SharePage />} />
        <Route path="/shared-activities" element={<SharedActivitiesPage />} />
        <Route path="/shared-goals" element={<SharedGoalsPage />} />
        <Route path="/notifications" element={<NotificationsPage />} />
      </Routes>
    </div>
  );
}

export default App;