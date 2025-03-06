package com.sp.FitnessTracker.dto;

import java.time.LocalDate;

import com.sp.FitnessTracker.entity.Goal;

public class GoalDTO {
    private Long id;
    private String description;
    private Double target;
    private String targetType;
    private UserDTO user; 
    private LocalDate startDate;
    private LocalDate endDate;
    private String username;

    public GoalDTO(Goal goal, String username) {
        this.id = goal.getId();
        this.description = goal.getDescription();
        this.target = goal.getTarget();
        this.targetType = goal.getTargetType();
        this.startDate = goal.getStartDate();
        this.endDate = goal.getEndDate();
        this.username = username;
    }
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTarget() {
		return target;
	}

	public void setTarget(Double target) {
		this.target = target;
	}
	

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    
}

