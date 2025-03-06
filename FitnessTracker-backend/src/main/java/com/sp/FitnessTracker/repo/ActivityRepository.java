package com.sp.FitnessTracker.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.FitnessTracker.entity.Activity;
import com.sp.FitnessTracker.entity.User;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	List<Activity> findByUser(User user);
	
	List<Activity> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
	
	Optional<Activity> findById(Long id);
}
