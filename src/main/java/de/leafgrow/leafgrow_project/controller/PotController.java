package de.leafgrow.leafgrow_project.controller;

import de.leafgrow.leafgrow_project.domain.dto.PotDto;
import de.leafgrow.leafgrow_project.domain.entity.Instruction;
import de.leafgrow.leafgrow_project.domain.entity.Pot;
import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.repository.PotRepository;
import de.leafgrow.leafgrow_project.service.interfaces.PotService;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pots")
public class PotController {
  private PotService service;
  private PotRepository repository;
  private UserService userService;

  public PotController(PotService service, PotRepository repository, UserService userService) {
    this.service = service;
    this.repository = repository;
    this.userService = userService;
  }

  @GetMapping("/{id}/instruction")
  public ResponseEntity<Instruction> getInstructionForPot(@PathVariable Long id) {
    Optional<Pot> potOptional = repository.findById(id);
    if (potOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Pot pot = potOptional.get();

    return ResponseEntity.ok(pot.getInstruction());
  }

  @PostMapping("/{id}/refresh")
  public ResponseEntity<Pot> refreshPot(@PathVariable Long id) {
    Optional<Pot> potOptional = repository.findById(id);
    if (potOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Pot pot = potOptional.get();

    service.refreshPot(pot);
    return ResponseEntity.ok(pot);
  }

  @PostMapping("/create-pot-admin")
  public ResponseEntity<Pot> createPotForAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user = userService.loadUserByEmail(email);
    Pot pot = service.createPotForAdmin(user);
    repository.save(pot);
    return ResponseEntity.ok(pot);
  }

  @PostMapping("/create")
  public ResponseEntity<Void> createPotsForUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user = userService.loadUserByEmail(email);
    service.createPotsForUser(user);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/activate")
  public ResponseEntity<Pot> activatePot(@PathVariable Long id) {
    Pot pot = service.activatePot(id);
    return ResponseEntity.ok(pot);
  }

  @PostMapping("/{id}/skip-day")
  // @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Pot> skipDay(@PathVariable Long id) {
    Optional<Pot> potOptional = repository.findById(id);
    if (potOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Pot pot = potOptional.get();
    service.skipDay(pot);
    return ResponseEntity.ok(pot);
  }

  @DeleteMapping("/admin/delete-pot-by-id")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      summary = "delete Pot by id",
      description = "Deleting a pot by their id, accessible only to" + " ADMIN users")
  public ResponseEntity<Map<String, Object>> deletePot(@RequestParam Long id) {
    try {
      Optional<Pot> potOptional = repository.findById(id);

      if (potOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      repository.deleteById(id);

      Map<String, Object> response = new HashMap<>();
      response.put("message", "Pot was successfully deleted");
      response.put("deletedPot", potOptional);

      return ResponseEntity.ok(response);

    } catch (ResponseStatusException e) {
      throw e;
    }
  }

  @GetMapping("/my")
  public ResponseEntity<List<PotDto>> getPotsForUser() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userContent = authentication.getName();
      User user = userService.loadUserByEmail(userContent);

      List<Pot> pots = service.findPotsByUserId(user.getId());
      List<PotDto> potsDto =
          pots.stream()
              .map(pot -> new PotDto(pot.getId(), pot.isActive(), pot.getInstruction()))
              .collect(Collectors.toList());
      return ResponseEntity.ok(potsDto);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Pot> deletePotById(@PathVariable Long id) {
    Pot pot = repository.findById(id).orElseThrow(() -> new RuntimeException("Pot not found"));
    repository.delete(pot);
    return ResponseEntity.ok(pot);
  }
}
