package com.sp.FitnessTracker.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import com.sp.FitnessTracker.dto.NotificationDTO;
import com.sp.FitnessTracker.dto.SharedActivityDTO;
import com.sp.FitnessTracker.dto.SharedGoalDTO;
import com.sp.FitnessTracker.dto.UserDTO;
import com.sp.FitnessTracker.entity.Activity;
import com.sp.FitnessTracker.entity.AuthResponse;
import com.sp.FitnessTracker.entity.Goal;
import com.sp.FitnessTracker.entity.LoginRequest;
import com.sp.FitnessTracker.entity.Notification;
import com.sp.FitnessTracker.entity.ShareGoalRequest;
import com.sp.FitnessTracker.entity.ShareRequest;
import com.sp.FitnessTracker.entity.SharedActivity;
import com.sp.FitnessTracker.entity.SharedGoal;
import com.sp.FitnessTracker.entity.User;
import com.sp.FitnessTracker.exception.CustomException;
import com.sp.FitnessTracker.models.Role;
import com.sp.FitnessTracker.repo.ActivityRepository;
import com.sp.FitnessTracker.repo.GoalRepository;
import com.sp.FitnessTracker.repo.NotificationRepository;
import com.sp.FitnessTracker.repo.SharedActivityRepository;
import com.sp.FitnessTracker.repo.SharedGoalRepository;
import com.sp.FitnessTracker.repo.UserRepository;

@Service
public class UserService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ActivityRepository activityRepository;

        @Autowired
        private NotificationRepository notificationRepository;

        @Autowired
        private SharedActivityRepository sharedActivityRepository;

        @Autowired
        private SharedGoalRepository sharedGoalRepository;

        @Autowired
        private GoalRepository goalRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private AuthenticationManager authManager;

        public User registerUser(User user) {

                if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                        throw new CustomException("Username already exists");
                }
                if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                        throw new CustomException("Email already exists");
                }
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRole(Role.ROLE_USER);
                return userRepository.save(user);
        }

        public User findByUsername(String username) {

                return userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        public ResponseEntity<AuthResponse> login(LoginRequest authRequest) throws Exception {

                User user = userRepository.findByUsername(authRequest.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                authManager.authenticate(
                                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                                                authRequest.getPassword()));

                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                List.of(new SimpleGrantedAuthority(user.getRole().name())));

                System.out.println("Entered Password: " + authRequest.getPassword());
                System.out.println("Stored Password (DB): " + user.getPassword());
                System.out
                                .println("Password Matches? " + passwordEncoder.matches(authRequest.getPassword(),
                                                user.getPassword()));

                final String token = jwtService.generateToken(userDetails);

                return ResponseEntity.ok(new AuthResponse(token));
        }

        public ResponseEntity<UserDTO> getUserProfile(String token) {

                String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
                String username = jwtService.extractUsername(jwtToken);

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                UserDTO userDTO = new UserDTO(user.getUsername());

                return ResponseEntity.ok(userDTO);

        }

        public void updateProfile(User updatedUser, String token) {
                String username = extractUsernameFromToken(token);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                user.setEmail(updatedUser.getEmail());
                userRepository.save(user);
        }

        public void updatePassword(String currentPassword, String newPassword, String token) {
                String username = extractUsernameFromToken(token);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                        throw new RuntimeException("Current password is incorrect.");
                }

                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
        }

        public void share(ShareRequest shareRequest, String token) throws AccessDeniedException {
                String senderUsername = extractUsernameFromToken(token);
                User sender = userRepository.findByUsername(senderUsername)
                                .orElseThrow(() -> new UsernameNotFoundException("Sender not found."));

                User recipient = userRepository.findByUsername(shareRequest.getRecipientUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("Recipient not found."));

                Activity activity = activityRepository.findById(shareRequest.getActivityId())
                                .orElseThrow(() -> new RuntimeException("Activity not found."));

                if (!activity.getUser().getId().equals(sender.getId())) {
                        throw new AccessDeniedException("You are not authorized to share this activity.");
                }

                SharedActivity sharedActivity = new SharedActivity();
                sharedActivity.setActivity(activity);
                sharedActivity.setSender(sender);
                sharedActivity.setRecipient(recipient);
                sharedActivity.setSharedAt(LocalDateTime.now());

                sharedActivityRepository.save(sharedActivity);

                Notification notification = new Notification();
                notification
                                .setMessage("You have a new shared activity from " + sender.getUsername() + ": "
                                                + activity.getType());
                notification.setCreatedAt(LocalDateTime.now());
                notification.setRead(false);
                notification.setUser(recipient);

                notificationRepository.save(notification);
        }

        public List<SharedActivityDTO> getSharedActivities(String token) {
                String username = extractUsernameFromToken(token);
                User recipient = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                List<SharedActivity> sharedActivities = sharedActivityRepository.findByRecipient(recipient);

                return sharedActivities.stream()
                                .map(SharedActivityDTO::new)
                                .collect(Collectors.toList());
        }

        public void deleteSharedActivity(Long id, String token) throws AccessDeniedException {
                String username = extractUsernameFromToken(token);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                SharedActivity sharedActivity = sharedActivityRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Shared activity not found."));

                if (!sharedActivity.getRecipient().getId().equals(user.getId())) {
                        throw new AccessDeniedException("You are not authorized to delete this shared activity.");
                }

                sharedActivityRepository.delete(sharedActivity);
        }

        public void shareGoal(ShareGoalRequest shareGoalRequest, String token) throws AccessDeniedException {
                String senderUsername = extractUsernameFromToken(token);
                User sender = userRepository.findByUsername(senderUsername)
                                .orElseThrow(() -> new UsernameNotFoundException("Sender not found."));

                User recipient = userRepository.findByUsername(shareGoalRequest.getRecipientUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("Recipient not found."));

                Goal goal = goalRepository.findById(shareGoalRequest.getGoalId())
                                .orElseThrow(() -> new RuntimeException("Goal not found."));

                if (!goal.getUser().getId().equals(sender.getId())) {
                        throw new AccessDeniedException("You are not authorized to share this goal.");
                }

                SharedGoal sharedGoal = new SharedGoal();
                sharedGoal.setGoal(goal);
                sharedGoal.setSender(sender);
                sharedGoal.setRecipient(recipient);
                sharedGoal.setSharedAt(LocalDateTime.now());

                sharedGoalRepository.save(sharedGoal);
        }

        public List<SharedGoalDTO> getSharedGoals(String token) {
                String username = extractUsernameFromToken(token);
                User recipient = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                List<SharedGoal> sharedGoals = sharedGoalRepository.findByRecipient(recipient);

                return sharedGoals.stream()
                                .map(SharedGoalDTO::new)
                                .collect(Collectors.toList());
        }

        public void deleteSharedGoal(Long id, String token) throws AccessDeniedException {
                String username = extractUsernameFromToken(token);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                SharedGoal sharedGoal = sharedGoalRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Shared goal not found."));

                if (!sharedGoal.getRecipient().getId().equals(user.getId())) {
                        throw new AccessDeniedException("You are not authorized to delete this shared goal.");
                }

                sharedGoalRepository.delete(sharedGoal);
        }

        public List<NotificationDTO> getNotificationsForUser(String token) {
                String username = extractUsernameFromToken(token);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                List<Notification> notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user);

                return notifications.stream()
                                .map(NotificationDTO::new)
                                .collect(Collectors.toList());
        }

        public void deleteNotification(Long id, String token) throws AccessDeniedException {
                String username = extractUsernameFromToken(token);
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

                Notification notification = notificationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Notification not found."));

                if (!notification.getUser().getId().equals(user.getId())) {
                        throw new AccessDeniedException("You are not authorized to delete this notification.");
                }

                notificationRepository.delete(notification);
        }

        private String extractUsernameFromToken(String token) {
                String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
                return jwtService.extractUsername(jwtToken);
        }

}
