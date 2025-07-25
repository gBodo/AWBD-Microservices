package com.example.authentification_service.Controller;

import com.example.authentification_service.RequestBody.LoginRequest;
import com.example.authentification_service.RequestBody.LoginResponse;
import com.example.authentification_service.RequestBody.RegistrationBody;
import com.example.authentification_service.Service.UserService;
import com.example.authentification_service.Util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User", description = "Register and login a user.")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(summary = "Register user.")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            String user = userService.registerUser(registrationBody);
            logger.info("A new user has registred.");
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create an account!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PostMapping("/login")
    @Operation(summary = "Login user.")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            // Generate JWT token
            final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Return the JWT token
            logger.info("User logged in.");
            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (Exception e) {
            // Handle authentication failure
            logger.error("Failed to login!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
