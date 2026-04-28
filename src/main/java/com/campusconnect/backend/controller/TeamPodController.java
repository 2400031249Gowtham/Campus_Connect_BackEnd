package com.campusconnect.backend.controller;

import com.campusconnect.backend.model.TeamPod;
import com.campusconnect.backend.model.TeamMember;
import com.campusconnect.backend.model.User;
import com.campusconnect.backend.repository.TeamPodRepository;
import com.campusconnect.backend.repository.TeamMemberRepository;
import com.campusconnect.backend.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team-pods")
@RequiredArgsConstructor
public class TeamPodController {

    private final TeamPodRepository teamPodRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<TeamPod>> getAll() {
        return ResponseEntity.ok(teamPodRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TeamPod pod, HttpSession session) {
        try {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                pod.setCreatedBy(userId);
            }
            pod.setCreatedAt(LocalDateTime.now());
            return ResponseEntity.ok(teamPodRepository.save(pod));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            teamPodRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- Member Management ---

    @PostMapping("/{podId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long podId,
                                       @RequestBody TeamMember member) {
        try {
            member.setPodId(podId);
            member.setJoinedAt(LocalDateTime.now());
            return ResponseEntity.ok(teamMemberRepository.save(member));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{podId}/members/{memberId}")
    public ResponseEntity<?> removeMember(@PathVariable Long podId,
                                          @PathVariable Long memberId) {
        try {
            teamMemberRepository.deleteById(memberId);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
