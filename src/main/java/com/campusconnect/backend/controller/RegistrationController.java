package com.campusconnect.backend.controller;

import com.campusconnect.backend.dto.StatusUpdateRequest;
import com.campusconnect.backend.model.Registration;
import com.campusconnect.backend.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(registrationService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Registration reg) {
        try {
            return ResponseEntity.ok(registrationService.create(reg));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id,
                                          @RequestBody StatusUpdateRequest req) {
        try {
            return ResponseEntity.ok(registrationService.updateStatus(id, req.getStatus()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            registrationService.delete(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
