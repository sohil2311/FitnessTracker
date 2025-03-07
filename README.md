# FitnessTracker

This project is a Fitness Tracker Application designed to help users track their fitness activities and goals. It is a full-stack application built with a Spring Boot backend and a React frontend. The application supports two types of users: Admin and Regular Users. Below is a detailed description of the project, including its features, architecture, and future enhancements.

1. Project Overview

   The Fitness Tracker Application allows users to:

   Log fitness activities (e.g., running, cycling) with details like type, duration, calories burned, and date.

   Set fitness goals with details like description, target, start date, and end date.

   View, edit, and delete their activities and goals.

   Admins can view all users' activities and goals and manage them.

   The application is secure, scalable, and responsive, ensuring a seamless user experience across devices.

2. Key Features

   a. User Features

   User Registration:

   Users can create an account by providing a username, email, and password.

   Passwords are hashed using BCrypt before being stored in the database.

   User Login:

   Users can log in using their username and password.

   Upon successful login, a JWT (JSON Web Token) is generated and stored in the browser's localStorage.

   Activity Management:

   Users can log activities with details like type, duration, calories burned, and date.

   Users can view, edit, or delete their activities.

   Goal Management:

   Users can set fitness goals with details like description, target, start date, and end date.

   Users can view, edit, or delete their goals.

   User Profile Page:

   Users can update their profile information (e.g., email, password).

   b. Admin Features

   View All Activities:

   Admins can view all activities logged by all users.

   View All Goals:

   Admins can view all goals set by all users.

   Edit and Delete:

   Admins can edit or delete any activity or goal.

   c. Additional Features

   Progress Tracking:

   Charts or graphs are provided to visualize progress toward fitness goals.

   Social Features:

   Users can share their activities and goals with friends.

   Notifications:

   Users receive reminders for upcoming goals or activities.

3. Architecture

   a. Backend (Spring Boot)

   The backend is built using Spring Boot and provides RESTful APIs for the frontend. It handles user authentication, activity and goal management, and data storage.

   Key Components

   User Authentication:

   Uses Spring Security for authentication and authorization.

   JWT tokens are used for secure communication between the frontend and backend.

   Activity and Goal Management:

   RESTful endpoints are provided for creating, reading, updating, and deleting activities and goals.

   Admins have access to endpoints for fetching all activities and goals.

   Database:

   The application uses a MySQL database to store user information, activities, and goals.

   Tables:

   User: Stores user details (username, email, password, role).

   Activity: Stores activity details (type, duration, calories burned, date, user ID).

   Goal: Stores goal details (description, target, start date, end date, user ID).

   Security:

   All requests (except login and registration) require a valid JWT token in the Authorization header.

   Role-based access control ensures that only admins can access certain endpoints.

   b. Frontend (React)

   The frontend is a single-page application (SPA) built with React. It provides a user-friendly interface for interacting with the backend.

   Key Components

   Pages:

   Home Page: Displays a welcome message and a brief description of the application.

   Login Page: Allows users to log in using their username and password.

   Register Page: Allows new users to register.

   Dashboard Page: Displays the user's activities and goals.

   Components:

   Navbar: Provides navigation links to the Home, Login, Register, and Dashboard pages.

   ActivityList: Displays a list of activities in a table format.

   GoalList: Displays a list of goals in a table format.

   AddActivity: Provides a form for users to log a new activity.

   AddGoal: Provides a form for users to set a new goal.

   EditActivityForm: Provides a form for users to edit an existing activity.

   EditGoalForm: Provides a form for users to edit an existing goal.

   State Management:

   Uses React's useState and useEffect hooks to manage component state and fetch data from the backend.

   JWT tokens are stored in localStorage to persist user sessions.

   Responsive Design:

   The application is fully responsive, ensuring it works well on mobile, tablet, and desktop devices.

   Media queries are used to adjust layouts, font sizes, and padding for different screen sizes.

4. Workflow

   a. User Workflow

   Register: A new user registers by providing a username, email, and password.

   Login: The user logs in using their credentials and receives a JWT token.

   Dashboard:

   The user can log activities and set goals.

   The user can view, edit, or delete their activities and goals.

   The user can update their profile information.

   b. Admin Workflow

   Login: The admin logs in using their credentials and receives a JWT token.

   Dashboard:

   The admin can view all activities and goals.

   The admin can edit or delete any activity or goal.

5. Technologies Used

   Backend

   Spring Boot: Framework for building the backend.

   Spring Security: For authentication and authorization.

   JWT: For secure communication between the frontend and backend.

   MySQL: Database for storing user information, activities, and goals.

   Maven: Dependency management.

   Frontend

   React: JavaScript library for building the user interface.

   Axios: For making HTTP requests to the backend.

   React Router: For navigation between pages.

   CSS: For styling the application.

6. How to Run the Project

   Backend

   Set up a MySQL database and update the application.properties file with your database credentials.

   Run the Spring Boot application using your IDE or the command line:

   mvn spring-boot:run

   Frontend

   Navigate to the frontend directory:
   cd frontend

   Install dependencies:
   npm install

   Start the development server:
   npm start

7. Conclusion

   This Fitness Tracker Application is a comprehensive solution for tracking fitness activities and goals. It demonstrates the use of modern technologies like Spring Boot, React, and JWT for building secure and scalable applications. With its user-friendly interface and robust backend, it provides a seamless experience for users and admins alike. Future enhancements like progress tracking, social features, and notifications will further improve its functionality and user engagement. Let me know if you need further clarification or assistance!
