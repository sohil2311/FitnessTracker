package com.sp.FitnessTracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sp.FitnessTracker.entity.SharedActivity;

public class SharedActivityDTO {
    private Long id;
    private String senderUsername;
    private String recipientUsername;
    private String activityType;
    private int duration;
    private int caloriesBurned;
    private LocalDate activityDate;
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
	public String getRecipientUsername() {
		return recipientUsername;
	}
	public void setRecipientUsername(String recipientUsername) {
		this.recipientUsername = recipientUsername;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getCaloriesBurned() {
		return caloriesBurned;
	}
	public void setCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}
	public LocalDate getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(LocalDate activityDate) {
		this.activityDate = activityDate;
	}
	public LocalDateTime getSharedAt() {
		return sharedAt;
	}
	public void setSharedAt(LocalDateTime sharedAt) {
		this.sharedAt = sharedAt;
	}
	
	public SharedActivityDTO(Long id, String senderUsername, String recipientUsername, String activityType,
			int duration, int caloriesBurned, LocalDate activityDate, LocalDateTime sharedAt) {
		super();
		this.id = id;
		this.senderUsername = senderUsername;
		this.recipientUsername = recipientUsername;
		this.activityType = activityType;
		this.duration = duration;
		this.caloriesBurned = caloriesBurned;
		this.activityDate = activityDate;
		this.sharedAt = sharedAt;
	}

	public SharedActivityDTO(SharedActivity sharedActivity) {
		this.id = sharedActivity.getId();
		this.senderUsername = sharedActivity.getSender().getUsername();
		this.recipientUsername = sharedActivity.getRecipient().getUsername();
		this.activityType = sharedActivity.getActivity().getType();
		this.duration = sharedActivity.getActivity().getDuration();
		this.caloriesBurned = sharedActivity.getActivity().getCaloriesBurned();
		this.activityDate = sharedActivity.getActivity().getDate();
		this.sharedAt = sharedActivity.getSharedAt();
	}
}
