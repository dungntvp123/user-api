package com.microservice.user.controller;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.data.dto.request.GoogleAuthRequestDto;
import com.microservice.user.data.dto.request.UsernamePasswordAuthRequestDto;
import com.microservice.user.data.dto.request.ValidateTokenRequestDto;
import com.microservice.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UsernamePasswordAuthRequestDto requestDto) {
        log.info("(authenticate)");
        ResponseBody<?> body = authService.authenticate(requestDto);
        return ResponseEntity.ok(body);
    }
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody ValidateTokenRequestDto requestDto) {
        ResponseBody<?> body = authService.validateToken(requestDto);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestParam String accountVerifyToken) {
        log.info("verify-account");
        ResponseBody<?> body = authService.verifyAccount(accountVerifyToken);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/send-verify-token")
    public ResponseEntity<?> sendVerifyToken(@RequestBody String email) {
        log.info("(get-verify-token)");
        ResponseBody<?> body = authService.sendVerifyToken(email);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/v1/google-authenticate")
    public ResponseEntity<?> googleAuthenticate(@RequestHeader("AuthorizeZ") String systemKey,
            @RequestBody GoogleAuthRequestDto requestDto) {
        ResponseBody<?> body = authService.googleAuthenticate(requestDto, systemKey);
        return ResponseEntity.ok(body);
    }
}
