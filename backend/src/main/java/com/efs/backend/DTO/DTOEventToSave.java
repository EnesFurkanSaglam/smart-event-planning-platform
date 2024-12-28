package com.efs.backend.DTO;

import com.efs.backend.Model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTOEventToSave {

    private Long eventId;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Location location;

    private Long organizerId;

    private LocalDateTime createdAt;

    private String category;

}
