package com.campusconnect.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "registrations",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "activity_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "activity_id", nullable = false)
    private Integer activityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.registered;

    public enum Status { registered, attended, cancelled }
}
