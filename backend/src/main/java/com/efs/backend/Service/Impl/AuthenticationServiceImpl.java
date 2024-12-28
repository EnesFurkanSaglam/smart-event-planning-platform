package com.efs.backend.Service.Impl;


import com.efs.backend.DAO.IRefreshTokenRepository;
import com.efs.backend.DAO.IUserRepository;
import com.efs.backend.DTO.*;
import com.efs.backend.Exception.BaseException;
import com.efs.backend.Exception.ErrorMessage;
import com.efs.backend.Exception.MessageType;
import com.efs.backend.JWT.JWTService;
import com.efs.backend.Model.RefreshToken;
import com.efs.backend.Model.User;
import com.efs.backend.Service.EmailService;
import com.efs.backend.Service.IAuthenticationService;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    private User createUser(AuthRequest input){
        User user = new User();

        user.setCreatedAt(LocalDateTime.now());
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return user;
    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        return refreshToken;
    }


    @Override
    public DTOUser register(AuthRequest input) {

        DTOUser dtoUser = new DTOUser();
        User savedUser = userRepository.save(createUser(input));

        BeanUtils.copyProperties(savedUser,dtoUser);

        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword());

            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optUser = userRepository.getByUsername(input.getUsername());

            String accessToken = jwtService.generateToken(optUser.get());
            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));

            return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());


        }catch (Exception e){

            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.USERNAME_OR_PASSWORD_INVALID));

        }

    }

    public boolean isValidRefreshToken(Date expiredDate){
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {

        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.getByRefreshToken(input.getRefreshToken());

        if (optRefreshToken.isEmpty()){
            throw new BaseException(new ErrorMessage(input.getRefreshToken(), MessageType.REFRESH_TOKEN_NOT_FOUND));
        }

        if (!isValidRefreshToken(optRefreshToken.get().getExpiredDate())){
            throw new BaseException(new ErrorMessage(input.getRefreshToken(), MessageType.REFRESH_TOKEN_HAS_EXPIRED));
        }

        User user = optRefreshToken.get().getUser();

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken = createRefreshToken(user);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());

    }

    @Override
    public void sendEmail(MailRequest mailRequest) {
        String newPassword = "0000";
        String email = mailRequest.getEmail();
        String username = mailRequest.getUsername();

        User user = userService.getUserByUsername(username);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(email, newPassword);
    }

}
