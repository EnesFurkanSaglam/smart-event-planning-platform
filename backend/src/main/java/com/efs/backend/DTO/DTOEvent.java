package com.efs.backend.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DTOEvent {

    private Long eventId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long locationId;

    private Long organizerId;

    private LocalDateTime createdAt;

    private String category;

}