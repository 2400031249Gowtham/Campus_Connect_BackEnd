package com.campusconnect.backend.repository;

import com.campusconnect.backend.model.TeamPod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamPodRepository extends JpaRepository<TeamPod, Long> {
}
