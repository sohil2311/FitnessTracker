package com.sp.FitnessTracker.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sp.FitnessTracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
}