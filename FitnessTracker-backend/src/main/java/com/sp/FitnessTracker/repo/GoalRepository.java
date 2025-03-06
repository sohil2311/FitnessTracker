package com.sp.FitnessTracker.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.FitnessTracker.entity.Goal;
import com.sp.FitnessTracker.entity.User;

public interface GoalRepository extends JpaRepository<Goal, Long> { 
	List<Goal> findByUser(User user);

	List<Goal> findByEndDate(LocalDate today);
}
