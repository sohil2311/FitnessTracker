package com.sp.FitnessTracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sp.FitnessTracker.dto.ActivityDTO;
import com.sp.FitnessTracker.entity.Activity;
import com.sp.FitnessTracker.entity.User;
import com.sp.FitnessTracker.models.Role;
import com.sp.FitnessTracker.repo.ActivityRepository;
import com.sp.FitnessTracker.repo.UserRepository;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public Activity logActivity(Activity activity, String token) {

        String usernameFromToken = extractUsernameFromToken(token);

        User user = userRepository.findByUsername(usernameFromToken)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameFromToken));

        activity.setUser(user);
        return activityRepository.save(activity);
    }

    public List<ActivityDTO> getAllActivities(String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Access denied. Admin role required.");
        }

        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
                .map(activity -> new ActivityDTO(activity, activity.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getUserActivities(String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Activity> activities = activityRepository.findByUser(user);
        return activities.stream()
                .map(activity -> new ActivityDTO(activity, activity.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    private String extractUsernameFromToken(String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return jwtService.extractUsername(jwtToken);
    }

    public void editActivity(Long id, Activity updatedActivity, String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            if (!activity.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException("You are not authorized to edit this activity.");
            }
        }

        activity.setType(updatedActivity.getType());
        activity.setDuration(updatedActivity.getDuration());
        activity.setCaloriesBurned(updatedActivity.getCaloriesBurned());
        activity.setDate(updatedActivity.getDate());

        activityRepository.save(activity);
    }

    public void deleteActivity(Long id, String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            if (!activity.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException("You are not authorized to delete this activity.");
            }
        }

        activityRepository.delete(activity);
    }
}
