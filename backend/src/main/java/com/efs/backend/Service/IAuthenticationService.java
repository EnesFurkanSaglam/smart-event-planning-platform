package com.efs.backend.Service;

import com.efs.backend.DTO.*;

public interface IAuthenticationService {

    DTOUser register(AuthRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);

    void sendEmail(MailRequest mailRequest);


}
