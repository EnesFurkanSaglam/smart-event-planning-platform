package com.efs.backend.Model;


import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event_participants")
@IdClass(EventParticipantId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventParticipant {

    @Id
    private Long eventId;

    @Id
    private Long userId;

    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();


}
