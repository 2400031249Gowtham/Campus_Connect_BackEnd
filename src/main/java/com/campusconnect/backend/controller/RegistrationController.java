package com.campusconnect.backend.controller;

import com.campusconnect.backend.dto.StatusUpdateRequest;
import com.campusconnect.backend.model.Registration;
import com.campusconnect.backend.service.RegistrationService;
import com.campusconnect.backend.service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(registrationService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Registration reg, HttpSession session) {
        try {
            Registration saved = registrationService.create(reg);
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                auditLogService.log("CREATE_REGISTRATION", "Registration", (long) saved.getId(),
                    userId, "User", "Registered for activity ID: " + saved.getActivityId());
            }
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id,
                                          @RequestBody StatusUpdateRequest req, HttpSession session) {
        try {
            Registration updated = registrationService.updateStatus(id, req.getStatus());
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                auditLogService.log("UPDATE_REGISTRATION", "Registration", (long) id,
                    userId, "User", "Updated status to: " + req.getStatus());
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpSession session) {
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                auditLogService.log("DELETE_REGISTRATION", "Registration", (long) id,
                    userId, "User", "Cancelled registration ID: " + id);
            }
            registrationService.delete(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
