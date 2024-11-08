package com.efs.backend.DTO;


import com.efs.backend.Model.Location;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DtoUser {

    private Long userId;

    private String username;

    private String email;

    private String password;

    private String profilePicture;

    private Location location;

    private LocalDateTime createdAt;
}
