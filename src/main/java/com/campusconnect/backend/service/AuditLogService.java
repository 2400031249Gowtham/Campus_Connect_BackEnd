package com.campusconnect.backend.service;

import com.campusconnect.backend.model.AuditLog;
import com.campusconnect.backend.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String action, String entityType, Long entityId,
                    Integer userId, String userName, String details) {
        AuditLog entry = new AuditLog();
        entry.setAction(action);
        entry.setEntityType(entityType);
        entry.setEntityId(entityId);
        entry.setUserId(userId);
        entry.setUserName(userName);
        entry.setDetails(details);
        entry.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(entry);
    }

    public List<AuditLog> getAll() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }
}
