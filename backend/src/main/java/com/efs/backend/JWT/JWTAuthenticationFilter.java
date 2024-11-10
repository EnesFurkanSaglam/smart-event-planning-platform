package com.efs.backend.JWT;


import com.efs.backend.Exception.BaseException;
import com.efs.backend.Exception.ErrorMessage;
import com.efs.backend.Exception.MessageType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private  JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header == null){
            filterChain.doFilter(request,response);
            return;
        }

        String token;
        String username;

        token = header.substring(7);

        try {
            username = jwtService.getUsernameByToken(token);
            if (username!= null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails!=null && jwtService.isTokenValid(token)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }
        catch (ExpiredJwtException ex){
            throw new BaseException(new ErrorMessage(ex.getMessage(), MessageType.TOKEN_IS_EXPIRED));
        }
        catch (Exception e){
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.GENERAL_EXCEPTION));
        }

    }
}
