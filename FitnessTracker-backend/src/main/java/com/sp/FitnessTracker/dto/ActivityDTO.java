package com.sp.FitnessTracker.dto;

import java.time.LocalDate;

import com.sp.FitnessTracker.entity.Activity;

public class ActivityDTO {
    private Long id;
    private String type;
    private int duration;
    private int caloriesBurned;
    private LocalDate date;
    private UserDTO user;
    private String username;

    public ActivityDTO(Activity activity, String username) {
        this.id = activity.getId();
        this.type = activity.getType();
        this.duration = activity.getDuration();
        this.caloriesBurned = activity.getCaloriesBurned();
        this.date = activity.getDate();
        this.username = username;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public int getCaloriesBurned() {
		return caloriesBurned;
	}

	public void setCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    
}

