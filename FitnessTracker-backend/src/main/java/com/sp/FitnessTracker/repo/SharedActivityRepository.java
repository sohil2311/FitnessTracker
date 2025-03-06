package com.sp.FitnessTracker.repo;

import com.sp.FitnessTracker.entity.SharedActivity;
import com.sp.FitnessTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedActivityRepository extends JpaRepository<SharedActivity, Long> {
    List<SharedActivity> findByRecipient(User recipient);
}