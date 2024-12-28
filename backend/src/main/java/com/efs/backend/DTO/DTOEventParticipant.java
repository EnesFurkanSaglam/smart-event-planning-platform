package com.efs.backend.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DTOEventParticipant {

    private Long eventId;

    private Long userId;

    private LocalDateTime joinedAt;

}