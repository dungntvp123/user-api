package com.microservice.user.controller;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.data.dto.request.ChangePasswordRequestDto;
import com.microservice.user.data.dto.request.LoadAccountListRequestDto;
import com.microservice.user.data.dto.request.LockAccountRequestDto;
import com.microservice.user.data.dto.request.RegisterRequestDto;
import com.microservice.user.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestHeader("ApiKey") String systemKey,
            @RequestBody RegisterRequestDto requestDto) {
        log.info("(register)");
        ResponseBody<?> body = accountService.register(requestDto, systemKey);
        return ResponseEntity.ok(body);
    }
    @PostMapping("/register-alt")
    public ResponseEntity<?> registerAlt(@RequestHeader("ApiKey") String systemKey,
            @RequestBody RegisterRequestDto requestDto) {
        log.info("(register)");
        ResponseBody<?> body = accountService.registerAlt(requestDto, systemKey);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/change-password/{accountId}")
    public ResponseEntity<?> changePassword(@PathVariable("accountId") String accountId,
            @RequestBody ChangePasswordRequestDto requestDto) {
        ResponseBody<?> body = accountService.changePassword(accountId, requestDto);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/lock-account/{accountId}")
    public ResponseEntity<?> lockAccount(@PathVariable("accountId") String accountId,
            @RequestBody LockAccountRequestDto requestDto) {
        ResponseBody<?> body = accountService.lock(accountId, requestDto);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/delete-account/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") String accountId) {
        ResponseBody<?> body = accountService.delete(accountId);
        return ResponseEntity.ok(body);
    }
    @PutMapping("/v1/restore-account/{accountId}")
    public ResponseEntity<?> restoreAccount(@PathVariable("accountId") String accountId) {
        ResponseBody<?> body = accountService.restore(accountId);
        return ResponseEntity.ok(body);
    }
    @GetMapping
    public ResponseEntity<?> loadAccountList(LoadAccountListRequestDto requestDto) {
        ResponseBody<?> body = accountService.loadAccount(requestDto);
        return ResponseEntity.ok(body);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<?> loadAccountDetail(@PathVariable("accountId") String accountId) {
        ResponseBody<?> body = accountService.loadAccount(accountId);
        return ResponseEntity.ok(body);
    }
}
