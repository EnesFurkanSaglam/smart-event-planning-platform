package com.efs.backend.Service;

import com.efs.backend.DTO.AuthRequest;
import com.efs.backend.DTO.AuthResponse;
import com.efs.backend.DTO.DtoUser;
import com.efs.backend.DTO.RefreshTokenRequest;
import com.efs.backend.Model.RefreshToken;

public interface IAuthenticationService {

    DtoUser register(AuthRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);


}
