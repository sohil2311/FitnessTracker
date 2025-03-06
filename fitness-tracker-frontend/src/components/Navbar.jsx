import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="navbar">
      <Link to="/" className="nav-link">Home</Link>
      <Link to="/login" className="nav-link">Login</Link>
      <Link to="/register" className="nav-link">Register</Link>
      <Link to="/dashboard" className="nav-link">Dashboard</Link>
      <Link to="/profile" className="nav-link">Profile</Link>
      <Link to="/progress" className="nav-link">Progress</Link>
      <Link to="/share" className="nav-link">Share</Link>
      <Link to="/shared-activities" className="nav-link">Shared Activities</Link>
      <Link to="/shared-goals" className="nav-link">Shared Goals</Link>
      <Link to="/notifications" className="nav-link">Notifications</Link>
    </nav>
  );
};

export default Navbar;