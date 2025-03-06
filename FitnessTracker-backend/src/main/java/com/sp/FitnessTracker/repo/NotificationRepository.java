package com.sp.FitnessTracker.repo;

import com.sp.FitnessTracker.entity.Notification;
import com.sp.FitnessTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}