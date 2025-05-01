package com.carpj.controller;

import com.carpj.model.Administrator;
import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;
import com.carpj.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = authService.userRegister(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error("用户注册失败", e);
            Map<String, String> response = new HashMap<>();
            response.put("error", "注册过程中发生错误");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名和密码不能为空");
            return ResponseEntity.badRequest().body(response);
        }

        User user = authService.userLogin(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/staff/login")
    public ResponseEntity<?> loginStaff(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名和密码不能为空");
            return ResponseEntity.badRequest().body(response);
        }

        MaintenanceStaff staff = authService.staffLogin(username, password);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名和密码不能为空");
            return ResponseEntity.badRequest().body(response);
        }

        Administrator admin = authService.adminLogin(username, password);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> passwordData) {
        Long userId = Long.parseLong(passwordData.get("userId"));
        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");

        if (userId == null || oldPassword == null || newPassword == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "用户ID、旧密码和新密码不能为空");
            return ResponseEntity.badRequest().body(response);
        }

        boolean success = authService.changeUserPassword(userId, oldPassword, newPassword);
        if (success) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "密码更改成功");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "密码更改失败，请检查旧密码是否正确");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/username-availability")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username, @RequestParam String userType) {
        try {
            boolean available = authService.isUsernameAvailable(username, userType);
            Map<String, Boolean> response = new HashMap<>();
            response.put("available", available);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/email-availability")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email, @RequestParam String userType) {
        try {
            boolean available = authService.isEmailAvailable(email, userType);
            Map<String, Boolean> response = new HashMap<>();
            response.put("available", available);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 