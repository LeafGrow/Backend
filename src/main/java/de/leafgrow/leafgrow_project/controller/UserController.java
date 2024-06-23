package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.exception_handling.Response;
import de.leafgrow.leafgrow_project.security.sec_dto.ChangePasswordRequestDto;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "UserController", description = "Controller for some operation " +
        "with user.")
public class UserController {
    private UserService service;
    private BCryptPasswordEncoder encoder;
    private JdbcTemplate jdbcTemplate;

    public UserController(UserService service, BCryptPasswordEncoder encoder,
                          JdbcTemplate jdbcTemplate) {
        this.service = service;
        this.encoder = encoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/profile")
    @Operation(
            summary = "get user info",
            description = "Receiving info about current user"
    )
    public ResponseEntity<User> getUserInfo() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userContent = authentication.getName();
            User user = service.loadUserByEmail(userContent);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/profile/delete-user")
    @Operation(
            summary = "delete user",
            description = "Deleting current user"
    )
    public ResponseEntity<Response> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = service.loadUserByEmail(email);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
            }

            service.delete(user);
            return ResponseEntity.ok(new Response("User was successfully deleted"));
        } catch (ResponseStatusException e) {
            throw e;
        }
    }

    @PatchMapping("/profile/change-password")
    @Operation(
            summary = "change user password",
            description = "Changing current user's password"
    )
    public ResponseEntity<Object> changeUserPassword(@RequestBody ChangePasswordRequestDto newPassword) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = service.loadUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(404).body(new Response("User not found"));
            }

            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            service.save(user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete-user-by-email")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "delete user by email",
            description = "Deleting a user by their email, accessible only to" +
                    " ADMIN users"
    )
    public ResponseEntity<Map<String, Object>> deleteUserByEmail(@RequestParam String email) {
        try {
            User user = service.loadUserByEmail(email);

            if(user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
            }

            service.delete(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User was successfully deleted");
            response.put("deletedUser", user);

            return ResponseEntity.ok(response);
        } catch(ResponseStatusException e) {
            throw e;
        }
    }

    @DeleteMapping("/admin/schema-reset") // VERY DANGEROUS METHOD !!!
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Reset the public schema",
            description = "Drops and recreates the public schema. Accessible only to ADMIN users."
    )
    public ResponseEntity<Response> resetSchema() {
        try {
            String dropSchemaSql = "DROP SCHEMA public CASCADE";
            String createSchemaSql = "CREATE SCHEMA public";

            jdbcTemplate.execute(dropSchemaSql);
            jdbcTemplate.execute(createSchemaSql);
            return ResponseEntity.ok(new Response("PUBLIC schema was successfully reset"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Failed to reset database schema: " + e.getMessage()));
        }
    }
}

