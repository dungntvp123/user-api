package com.microservice.user.service;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.data.dto.request.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    public ResponseBody<?> authenticate(UsernamePasswordAuthRequestDto requestDto);
    public ResponseBody<?> validateToken(ValidateTokenRequestDto requestDto);
    public ResponseBody<?> verifyAccount(String token);
    public ResponseBody<?> sendVerifyToken(String email);
    public ResponseBody<?> googleAuthenticate(GoogleAuthRequestDto requestDto, String systemKey);

}
