package com.efs.backend.Controller;

import com.efs.backend.DTO.AuthRequest;
import com.efs.backend.DTO.AuthResponse;
import com.efs.backend.DTO.DtoUser;
import com.efs.backend.DTO.RefreshTokenRequest;
import com.efs.backend.Service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationController extends RestBaseController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest input){
        return ok(authenticationService.register(input));
    }

    @PostMapping("/authenticate")
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input){
        return ok(authenticationService.authenticate(input));
    }

    @PostMapping("/refresh-token")
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest input){
        return ok(authenticationService.refreshToken(input));
    }

}
