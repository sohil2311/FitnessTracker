package com.sp.FitnessTracker.entity;

public class ShareRequest {
    private Long activityId;
    private String recipientUsername;
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getRecipientUsername() {
		return recipientUsername;
	}
	public void setRecipientUsername(String recipientUsername) {
		this.recipientUsername = recipientUsername;
	}
	public ShareRequest(Long activityId, String recipientUsername) {
		super();
		this.activityId = activityId;
		this.recipientUsername = recipientUsername;
	}

    
}
