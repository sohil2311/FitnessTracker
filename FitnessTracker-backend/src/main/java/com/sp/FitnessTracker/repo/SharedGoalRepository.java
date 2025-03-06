package com.sp.FitnessTracker.repo;

import com.sp.FitnessTracker.entity.SharedGoal;
import com.sp.FitnessTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedGoalRepository extends JpaRepository<SharedGoal, Long> {
    List<SharedGoal> findByRecipient(User recipient);
}