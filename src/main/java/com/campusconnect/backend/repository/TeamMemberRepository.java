package com.campusconnect.backend.repository;

import com.campusconnect.backend.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByPodId(Long podId);
    List<TeamMember> findByUserId(Integer userId);
}
