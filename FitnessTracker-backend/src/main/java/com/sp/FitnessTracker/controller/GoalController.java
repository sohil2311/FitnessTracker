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

import com.sp.FitnessTracker.dto.GoalDTO;
import com.sp.FitnessTracker.entity.Goal;
import com.sp.FitnessTracker.entity.GoalProgress;
import com.sp.FitnessTracker.service.GoalService;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/add")
    public ResponseEntity<String> setGoal(@RequestBody Goal goal, @RequestHeader("Authorization") String token) {
        goalService.setGoal(goal, token);
        return ResponseEntity.ok("Goal added successfully...");
    }

 
    @GetMapping
    public ResponseEntity<List<GoalDTO>> getAllGoals(@RequestHeader("Authorization") String token) {
        List<GoalDTO> goals = goalService.getAllGoals(token);
        return ResponseEntity.ok(goals);
    }

    
    @GetMapping("/user")
    public ResponseEntity<List<GoalDTO>> getUserGoals(@RequestHeader("Authorization") String token) {
        List<GoalDTO> goals = goalService.getUserGoals(token);
        return ResponseEntity.ok(goals);
    }
    
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editGoal(
            @PathVariable Long id,
            @RequestBody Goal updatedGoal,
            @RequestHeader("Authorization") String token) {
        try {
            goalService.editGoal(id, updatedGoal, token);
            return ResponseEntity.ok("Goal updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update goal.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGoal(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        try {
            goalService.deleteGoal(id, token);
            return ResponseEntity.ok("Goal deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete goal.");
        }
    }
    
    @GetMapping("/progress")
    public ResponseEntity<List<GoalProgress>> getProgress(@RequestHeader("Authorization") String token) {
        try {
            List<GoalProgress> progress = goalService.getProgress(token);
            return ResponseEntity.ok(progress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}