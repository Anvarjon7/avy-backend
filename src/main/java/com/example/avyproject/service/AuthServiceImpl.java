package com.example.avyproject.service;

import com.example.avyproject.entity.AvyUser;
import com.example.avyproject.exceptions.PasswordIncorrectException;
import com.example.avyproject.security.JwtRequest;
import com.example.avyproject.security.JwtResponse;
import com.example.avyproject.security.RefreshJwtRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AvyUserService avyUserService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public JwtResponse logIn(JwtRequest jwtRequest) {
        validateLogin(jwtRequest); //check the login is not null
        AvyUser avyUser = avyUserService.getByLogin(jwtRequest.getLogin());//get user from DB by login
        validatePassword(jwtRequest, avyUser); //check if the incoming login equals login in DB
        return new JwtResponse(jwtService.generateAccessToken(avyUser), null); //return access token
    }

    public void validatePassword(JwtRequest jwtRequest, AvyUser avyUser) {
        if (!passwordEncoder.matches(jwtRequest.getPassword(), avyUser.getPassword())) {
            throw new PasswordIncorrectException("Entered password is not correct");
        }
    }

    public void validateLogin(JwtRequest jwtRequest) {
        if (StringUtils.isEmpty(jwtRequest.getLogin())) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
    }
    @Override
    public JwtResponse getAccessToken(RefreshJwtRequest jwtRequest) {
        return null;
    }

    @Override
    public JwtResponse getRefreshToken(RefreshJwtRequest jwtRequest) {
        return null;
    }
}
