package com.sp.FitnessTracker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sp.FitnessTracker.dto.GoalDTO;
import com.sp.FitnessTracker.entity.Activity;
import com.sp.FitnessTracker.entity.Goal;
import com.sp.FitnessTracker.entity.GoalProgress;
import com.sp.FitnessTracker.entity.Notification;
import com.sp.FitnessTracker.entity.User;
import com.sp.FitnessTracker.models.Role;
import com.sp.FitnessTracker.repo.ActivityRepository;
import com.sp.FitnessTracker.repo.GoalRepository;
import com.sp.FitnessTracker.repo.NotificationRepository;
import com.sp.FitnessTracker.repo.UserRepository;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public Goal setGoal(Goal goal, String token) {
    	
    	String usernameFromToken = extractUsernameFromToken(token);
    	
        User user = userRepository.findByUsername(usernameFromToken)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameFromToken));
        
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    public List<GoalDTO> getAllGoals(String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Access denied. Admin role required.");
        }

        List<Goal> goals = goalRepository.findAll();
        return goals.stream()
                .map(goal -> new GoalDTO(goal, goal.getUser().getUsername())) 
                .collect(Collectors.toList());
    }

    public List<GoalDTO> getUserGoals(String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Goal> goals = goalRepository.findByUser(user);
        return goals.stream()
                .map(goal -> new GoalDTO(goal, goal.getUser().getUsername())) 
                .collect(Collectors.toList());
    }
    
    
    public void editGoal(Long id, Goal updatedGoal, String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            if (!goal.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException("You are not authorized to edit this goal.");
            }
        }

        goal.setDescription(updatedGoal.getDescription());
        goal.setTarget(updatedGoal.getTarget());
        goal.setStartDate(updatedGoal.getStartDate());
        goal.setEndDate(updatedGoal.getEndDate());

        goalRepository.save(goal);
    }

    public void deleteGoal(Long id, String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            if (!goal.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException("You are not authorized to delete this goal.");
            }
        }

        goalRepository.delete(goal);
    }
    
    
    public List<GoalProgress> getProgress(String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Goal> goals = goalRepository.findByUser(user);
        return goals.stream()
                .map(goal -> new GoalProgress(goal, calculateProgress(goal, user)))
                .collect(Collectors.toList());
    }

    private double calculateProgress(Goal goal, User user) {
        List<Activity> activities = activityRepository.findByUserAndDateBetween(
                user,
                goal.getStartDate(),
                goal.getEndDate()
        );

        switch (goal.getTargetType()) {
            case "CALORIES":
                double totalCaloriesBurned = activities.stream()
                        .mapToDouble(Activity::getCaloriesBurned)
                        .sum();
                return totalCaloriesBurned / (goal.getTarget());

            case "DURATION":
                double totalDuration = activities.stream()
                        .mapToDouble(Activity::getDuration)
                        .sum();
                return totalDuration / (goal.getTarget());


            default:
                return 0.0; 
        }
    }
    
    public void checkGoalDueDates() {
        LocalDate today = LocalDate.now();
        List<Goal> dueGoals = goalRepository.findByEndDate(today);

        for (Goal goal : dueGoals) {
            Notification notification = new Notification();
            notification.setMessage("Your goal '" + goal.getDescription() + "' is due today!");
            notification.setCreatedAt(LocalDateTime.now());
            notification.setRead(false);
            notification.setUser(goal.getUser());

            notificationRepository.save(notification);
        }
    }
    
    private String extractUsernameFromToken(String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return jwtService.extractUsername(jwtToken);
    }
}