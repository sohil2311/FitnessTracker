package com.sp.FitnessTracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sp.FitnessTracker.entity.SharedGoal;

public class SharedGoalDTO {
    private Long id;
    private String senderUsername;
    private String goalDescription;
    private Double goalTarget;
    private LocalDate goalStartDate;
    private LocalDate goalEndDate;
    private LocalDateTime sharedAt;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public Double getGoalTarget() {
        return goalTarget;
    }

    public void setGoalTarget(Double goalTarget) {
        this.goalTarget = goalTarget;
    }

    public LocalDate getGoalStartDate() {
        return goalStartDate;
    }

    public void setGoalStartDate(LocalDate goalStartDate) {
        this.goalStartDate = goalStartDate;
    }

    public LocalDate getGoalEndDate() {
        return goalEndDate;
    }

    public void setGoalEndDate(LocalDate goalEndDate) {
        this.goalEndDate = goalEndDate;
    }

    public LocalDateTime getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(LocalDateTime sharedAt) {
        this.sharedAt = sharedAt;
    }

	public SharedGoalDTO(Long id, String senderUsername, String goalDescription, double goalTarget,
			LocalDate goalStartDate, LocalDate goalEndDate, LocalDateTime sharedAt) {
		super();
		this.id = id;
		this.senderUsername = senderUsername;
		this.goalDescription = goalDescription;
		this.goalTarget = goalTarget;
		this.goalStartDate = goalStartDate;
		this.goalEndDate = goalEndDate;
		this.sharedAt = sharedAt;
	}
    
	public SharedGoalDTO (SharedGoal sharedGoal) {
        this.id = sharedGoal.getId();
        this.senderUsername = sharedGoal.getSender().getUsername();
        this.goalDescription = sharedGoal.getGoal().getDescription();
        this.goalTarget = sharedGoal.getGoal().getTarget();
        this.goalStartDate = sharedGoal.getGoal().getStartDate();
        this.goalEndDate = sharedGoal.getGoal().getEndDate();
        this.sharedAt = sharedGoal.getSharedAt();
       
    }
}
