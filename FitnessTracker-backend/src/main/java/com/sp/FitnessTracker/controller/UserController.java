package com.sp.FitnessTracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.FitnessTracker.dto.NotificationDTO;
import com.sp.FitnessTracker.dto.SharedActivityDTO;
import com.sp.FitnessTracker.dto.SharedGoalDTO;
import com.sp.FitnessTracker.entity.LoginRequest;
import com.sp.FitnessTracker.entity.Notification;
import com.sp.FitnessTracker.entity.ShareGoalRequest;
import com.sp.FitnessTracker.entity.ShareRequest;
import com.sp.FitnessTracker.entity.SharedActivity;
import com.sp.FitnessTracker.entity.SharedGoal;
import com.sp.FitnessTracker.entity.User;
import com.sp.FitnessTracker.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) {
    	
        try {
            return ResponseEntity.ok(userService.login(authRequest));
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    


    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        
        try {
            return ResponseEntity.ok(userService.getUserProfile(token));
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    
    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(
            @RequestBody User updatedUser,
            @RequestHeader("Authorization") String token) {
        try {
            userService.updateProfile(updatedUser, token);
            return ResponseEntity.ok("Profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile.");
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @RequestBody Map<String, String> passwordData,
            @RequestHeader("Authorization") String token) {
        try {
            userService.updatePassword(passwordData.get("currentPassword"), passwordData.get("newPassword"), token);
            return ResponseEntity.ok("Password updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password.");
        }
    }
    
    @PostMapping("/share")
    public ResponseEntity<String> share(
            @RequestBody ShareRequest shareRequest,
            @RequestHeader("Authorization") String token) {
        try {
            userService.share(shareRequest, token);
            return ResponseEntity.ok("Activity shared successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to share activity.");
        }
    }
    
    @GetMapping("/shared-activities")
    public ResponseEntity<List<SharedActivityDTO>> getSharedActivities(@RequestHeader("Authorization") String token) {
        try {
            List<SharedActivityDTO> sharedActivities = userService.getSharedActivities(token);
            return ResponseEntity.ok(sharedActivities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping("/shared-activities/{id}")
    public ResponseEntity<String> deleteSharedActivity(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        try {
            userService.deleteSharedActivity(id, token);
            return ResponseEntity.ok("Shared activity deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete shared activity.");
        }
    }
    
    @PostMapping("/share-goal")
    public ResponseEntity<String> shareGoal(
            @RequestBody ShareGoalRequest shareGoalRequest,
            @RequestHeader("Authorization") String token) {
        try {
            userService.shareGoal(shareGoalRequest, token);
            return ResponseEntity.ok("Goal shared successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to share goal.");
        }
    }
    
    @GetMapping("/shared-goals")
    public ResponseEntity<List<SharedGoalDTO>> getSharedGoals(@RequestHeader("Authorization") String token) {
        try {
            List<SharedGoalDTO> sharedGoals = userService.getSharedGoals(token);
            return ResponseEntity.ok(sharedGoals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping("/shared-goals/{id}")
    public ResponseEntity<String> deleteSharedGoal(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        try {
            userService.deleteSharedGoal(id, token);
            return ResponseEntity.ok("Shared goal deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete shared goal.");
        }
    }
    
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@RequestHeader("Authorization") String token) {
        try {
            List<NotificationDTO> notifications = userService.getNotificationsForUser(token);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        try {
            userService.deleteNotification(id, token);
            return ResponseEntity.ok("Notification deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete notification.");
        }
    }
}

