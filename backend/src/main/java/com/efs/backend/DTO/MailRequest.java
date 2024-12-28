package com.efs.backend.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MailRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String username;
}












