package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.exception_handling.Response;
import de.leafgrow.leafgrow_project.service.interfaces.ConfirmationService;
import de.leafgrow.leafgrow_project.service.interfaces.EmailService;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private UserService userService;
    private ConfirmationService confirmationService;
    private EmailService emailService;
    private PotService potService;


    public RegistrationController(UserService userService, ConfirmationService confirmationService, EmailService emailService, PotService potService) {
        this.userService = userService;
        this.confirmationService = confirmationService;
        this.emailService = emailService;
        this.potService = potService;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        return email.matches(regex);
    }

    @PostMapping
    @Operation(
            summary = "register",
            description = "Registration required user"
    )
    public ResponseEntity<Object> register(@RequestBody User user) {
        try {
            if (!isValidEmail(user.getEmail())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new Response("Email is incorrect."));
            }

            userService.register(user);
            potService.createPotsForUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response("Error with user registration: " + e.getMessage()));
        }
    }

    @GetMapping("/resent")
    @Operation(
            summary = "resent",
            description = "Resenting confirmation to email"
    )
    public ResponseEntity<Response> resendConfirmation(@RequestParam String email) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();

        User user = userService.loadUserByEmail(email);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response("User with such an email not found."));
        }
        if (user.isActive()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response("This account is already activated."));
        }
        emailService.sendConfirmationEmail(user);
        return ResponseEntity.ok(new Response("Registration confirmation has been resent. Please check your mailbox."));

    }

    @GetMapping("/confirm")
    @Operation(
            summary = "confirm",
            description = "Confirming user's registration"
    )
    public ResponseEntity<Response> confirmAccount(@RequestParam String code) {
        try {
            User confirmedUser = confirmationService.confirmUser(code);
            return ResponseEntity
                    .ok(new Response("Account is successfully activated. Now you can log in."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.REQUEST_TIMEOUT)
                    .body(new Response("Activating error: " + e.getMessage()));
        }
    }
}