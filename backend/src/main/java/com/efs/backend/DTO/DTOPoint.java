package com.efs.backend.DTO;


import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DTOPoint {

    private Long pointId;

    private String point;

    private LocalDateTime createdAt;

    private Long userId;

}

