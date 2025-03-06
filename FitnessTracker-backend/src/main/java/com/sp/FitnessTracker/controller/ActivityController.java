package com.sp.FitnessTracker.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sp.FitnessTracker.dto.ActivityDTO;
import com.sp.FitnessTracker.entity.Activity;
import com.sp.FitnessTracker.entity.ShareRequest;
import com.sp.FitnessTracker.service.ActivityService;
import com.sp.FitnessTracker.service.UserService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> logActivity(@RequestBody Activity activity, @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Received activity: " + activity);
            activityService.logActivity(activity, token);
            return ResponseEntity.ok("Activity added successfully...");
        } catch (Exception e) {
            System.err.println("Error adding activity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add activity.");
        }
    }

 
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities(@RequestHeader("Authorization") String token) {
        List<ActivityDTO> activities = activityService.getAllActivities(token);
        return ResponseEntity.ok(activities);
    }

    
    @GetMapping("/user")
    public ResponseEntity<List<ActivityDTO>> getUserActivities(@RequestHeader("Authorization") String token) {
        List<ActivityDTO> activities = activityService.getUserActivities(token);
        return ResponseEntity.ok(activities);
    }
    
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editActivity(
            @PathVariable Long id,
            @RequestBody Activity updatedActivity,
            @RequestHeader("Authorization") String token) {
        try {
            activityService.editActivity(id, updatedActivity, token);
            return ResponseEntity.ok("Activity updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update activity.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivity(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        try {
            activityService.deleteActivity(id, token);
            return ResponseEntity.ok("Activity deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete activity.");
        }
    }
    
    @PostMapping("/share")
    public ResponseEntity<String> share(
            @RequestBody ShareRequest shareRequest,
            @RequestHeader("Authorization") String token) {
        try {
            userService.share(shareRequest, token);
            return ResponseEntity.ok("Shared successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to share.");
        }
    }
}

