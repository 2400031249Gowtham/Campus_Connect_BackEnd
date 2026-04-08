package com.campusconnect.backend.repository;

import com.campusconnect.backend.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    List<Registration> findByUserId(Integer userId);
    List<Registration> findByActivityId(Integer activityId);
    Optional<Registration> findByUserIdAndActivityId(Integer userId, Integer activityId);
    void deleteByActivityId(Integer activityId);
}
