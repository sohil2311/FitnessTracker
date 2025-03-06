package com.sp.FitnessTracker.dto;

import java.time.LocalDateTime;

import com.sp.FitnessTracker.entity.Notification;

public class NotificationDTO {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
    
    public NotificationDTO (Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.createdAt = notification.getCreatedAt();
        this.isRead = notification.isRead();
    }
}
