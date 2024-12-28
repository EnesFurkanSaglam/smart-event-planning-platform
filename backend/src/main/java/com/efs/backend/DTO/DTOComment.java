package com.efs.backend.DTO;


import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DTOComment {

    private Long commentId;

    private Long eventId;

    private Long messageSenderId;

    private String content;

    private LocalDateTime createdAt;

}
