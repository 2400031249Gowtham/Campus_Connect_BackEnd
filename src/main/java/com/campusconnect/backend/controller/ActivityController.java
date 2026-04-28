package com.campusconnect.backend.controller;

import com.campusconnect.backend.model.Activity;
import com.campusconnect.backend.service.ActivityService;
import com.campusconnect.backend.service.AuditLogService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(activityService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Activity activity, HttpSession session) {
        try {
            Activity saved = activityService.create(activity);
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                auditLogService.log("CREATE_ACTIVITY", "Activity", (long) saved.getId(),
                    userId, "Admin", "Created activity: " + saved.getName());
            }
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Activity updates, HttpSession session) {
        try {
            Activity updated = activityService.update(id, updates);
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                auditLogService.log("UPDATE_ACTIVITY", "Activity", (long) id,
                    userId, "Admin", "Updated activity: " + updated.getName());
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
                auditLogService.log("DELETE_ACTIVITY", "Activity", (long) id,
                    userId, "Admin", "Deleted activity ID: " + id);
            }
            activityService.delete(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
