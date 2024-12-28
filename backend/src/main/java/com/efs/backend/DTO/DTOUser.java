package com.efs.backend.DTO;

import com.efs.backend.Enum.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DTOUser {

    private Long userId;

    private String username;

    private String email;

    private String password;

    private String profilePicture;

    private LocalDateTime createdAt;

    private String name;

    private String surname;

    private String phone;

    private Gender gender;

    private String interest;

}
