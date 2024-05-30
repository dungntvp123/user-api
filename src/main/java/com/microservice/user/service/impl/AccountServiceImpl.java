package com.microservice.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import com.microservice.user.data.dto.request.*;
import com.microservice.user.data.dto.response.LoadAccountResponseDto;
import com.microservice.user.data.dto.response.OauthAuthenticationResponseDto;
import com.microservice.user.data.dto.response.UserInfoResponseDto;
import com.microservice.user.data.dto.response.ValidateTokenResponseDto;
import com.microservice.user.data.entity.Account;
import com.microservice.user.data.entity.AccountVerifyToken;
import com.microservice.user.data.entity.User;
import com.microservice.user.exceptions.AccountVerifyTokenExpiredException;
import com.microservice.user.exceptions.RegisterInformationConstraintViolateException;
import com.microservice.user.exceptions.ResetPasswordInformationConstraintViolateException;
import com.microservice.user.repository.AccountRepository;
import com.microservice.user.repository.AccountVerifyTokenRepository;
import com.microservice.user.service.AccountService;
import com.microservice.user.utils.EmailUtils;
import com.microservice.user.utils.GoogleUtils;
import com.microservice.user.utils.JwtUtils;
import com.microservice.user.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private GoogleUtils googleUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private AccountVerifyTokenRepository accountVerifyTokenRepository;
    @Autowired
    private EmailUtils emailUtils;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public ResponseBody<?> authenticate(UsernamePasswordAuthRequestDto requestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
        }
        catch (Exception ex) {
            throw ex;
        }

        String jwt = jwtUtils.generateToken(requestDto.getUsername());
        return new ResponseBody<>(ResponseStatus.AUTHENTICATION_SUCCESSFUL, jwt);
    }

    @Override
    public ResponseBody<?> validateToken(ValidateTokenRequestDto requestDto) {
        String username;
        boolean isValid;
        try {
            username = jwtUtils.extractUsername(requestDto.getToken());
        } catch (Exception ex) {
            return new ResponseBody<>(ResponseStatus.VALIDATE_TOKEN_SUCCESSFUL,
                    new ValidateTokenResponseDto(false));
        }
        Optional<Account> opt = accountRepository.findByUsername(username);
        boolean accountAvailable = opt.get().isAccountNonExpired() &&
                opt.get().isAccountNonLocked() && opt.get().isEnabled();

        isValid = !jwtUtils.isTokenExpired(requestDto.getToken());

        String accessToken = isValid && accountAvailable ? jwtUtils.generateToken(username) : null;

        return new ResponseBody<>(ResponseStatus.VALIDATE_TOKEN_SUCCESSFUL,
                new ValidateTokenResponseDto(isValid, accessToken, new LoadAccountResponseDto().getDetail(opt.get())));

    }

    @Override
    @Transactional
    public ResponseBody<?> register(RegisterRequestDto requestDto, String systemKey) {
        List<String> violations = validationUtils.getViolationMessage(requestDto);
        if (accountRepository.existsByUsername(requestDto.getUsername())) {
            violations.add("Username must be unique");
        }
        if (accountRepository.existsByUserEmail(requestDto.getUsername())) {
            violations.add("Username must be unique");
        }
        if (!violations.isEmpty()) {
            try {
                String message = (new ObjectMapper()).writeValueAsString(violations);
                throw new RegisterInformationConstraintViolateException(message);
            } catch (JsonProcessingException ignored) {
            }
        }
        User user = User.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getFullName())
                .build();

        Account account = Account.builder()
                .username(requestDto.getUsername())
                .user(user)
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(requestDto.getRoles())
                .isExpired(true)
                .createDate(new Date(System.currentTimeMillis()))
                .lockAccountExpiration(new Timestamp(System.currentTimeMillis() - 1))
                .isEnabled(true)
                .systemKey(systemKey)
                .build();
        user.setAccount(account);
        AccountVerifyToken accountVerifyToken = new AccountVerifyToken(account);

        String token = accountVerifyTokenRepository.save(accountVerifyToken).getId();

        return new ResponseBody<>(ResponseStatus.REGISTRATION_SUCCESSFUL, token);
    }

    @Override
    @Transactional
    public ResponseBody<?> registerAlt(RegisterRequestDto requestDto, String systemKey) {
        List<String> violations = validationUtils.getViolationMessage(requestDto);
        if (accountRepository.existsByUsername(requestDto.getUsername())) {
            violations.add("Username must be unique");
        }
        if (accountRepository.existsByUserEmail(requestDto.getUsername())) {
            violations.add("Username must be unique");
        }
        if (!violations.isEmpty()) {
            try {
                String message = (new ObjectMapper()).writeValueAsString(violations);
                throw new RegisterInformationConstraintViolateException(message);
            } catch (JsonProcessingException ignored) {
            }
        }
        User user = User.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getFullName())
                .build();

        Account account = Account.builder()
                .username(requestDto.getUsername())
                .user(user)
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(requestDto.getRoles())
                .isExpired(false)
                .createDate(new Date(System.currentTimeMillis()))
                .lockAccountExpiration(new Timestamp(System.currentTimeMillis() - 1))
                .isEnabled(true)
                .systemKey(systemKey)
                .build();
        user.setAccount(account);

        String token = jwtUtils.generateToken(accountRepository.save(account).getUsername());

        return new ResponseBody<>(ResponseStatus.REGISTRATION_SUCCESSFUL, token);
    }

    @Override
    @Transactional
    public ResponseBody<?> lock(String accountId, LockAccountRequestDto requestDto) {
        Account account = accountRepository.findById(accountId).get();
        account.setLockAccountExpiration(new Timestamp(System.currentTimeMillis() + requestDto.getLockedTimeInLong()));

        accountRepository.save(account);
        return new ResponseBody<>(ResponseStatus.LOCK_ACCOUNT_SUCCESSFUL);
    }

    @Override
    @Transactional
    public ResponseBody<?> delete(String accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setEnabled(false);

        accountRepository.save(account);
        return new ResponseBody<>(ResponseStatus.DELETE_ACCOUNT_SUCCESSFUL);
    }

    @Override
    @Transactional
    public ResponseBody<?> restore(String accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setEnabled(true);

        accountRepository.save(account);
        return new ResponseBody<>(ResponseStatus.RESTORE_ACCOUNT_SUCCESSFUL);
    }

    @Override
    public ResponseBody<?> loadAccount(LoadAccountListRequestDto requestDto) {
        requestDto.getFormatted();
        Specification<Account> specification =
                hasUsernameLike(requestDto.getUsername())
                        .and(hasEmailLike(requestDto.getEmail()));
        Pageable page = PageRequest.of(requestDto.getPageIndex()-1, 20);

        if (requestDto.getOrderByUsername() != null) {
            page = pageOrderByUsername(requestDto.getPageIndex()-1, 20, requestDto.getOrderByUsername());
        }
        if (requestDto.getOrderByCreateDate() != null) {
            page = pageOrderByCreateDate(requestDto.getPageIndex()-1, 20, requestDto.getOrderByCreateDate());
        }


        Page<LoadAccountResponseDto> accounts = accountRepository.findAll(specification, page)
                .map(account -> new LoadAccountResponseDto().getBrief(account));
        return new ResponseBody<>(ResponseStatus.LOAD_ACCOUNT_LIST_SUCCESSFUL, accounts);
    }

    @Override
    public ResponseBody<?> loadAccount(String accountId) {
        Account account = accountRepository.findById(accountId).get();
        return new ResponseBody<>(ResponseStatus.LOAD_ACCOUNT_DETAIL_SUCCESSFUL,
                new LoadAccountResponseDto().getDetail(account));
    }

    @Override
    @Transactional
    public ResponseBody<?> verifyAccount(String token) {
        AccountVerifyToken accountVerifyToken = accountVerifyTokenRepository.findById(token).get();
        if (accountVerifyToken.isExpired()) {
            throw new AccountVerifyTokenExpiredException();
        }
        accountVerifyToken.getAccount().setExpired(false);
        accountVerifyTokenRepository.save(accountVerifyToken);
        String accessToken = jwtUtils.generateToken(accountVerifyToken.getAccount().getUsername());
        return new ResponseBody<>(ResponseStatus.ACCOUNT_VERIFICATION_SUCCESSFUL, accessToken);
    }

    @Override
    @Transactional
    public ResponseBody<?> sendVerifyToken(String email) {
        Account account = accountRepository.findByUserEmail(email).get();
        AccountVerifyToken verifyToken = new AccountVerifyToken(account);

        String token = accountVerifyTokenRepository.save(verifyToken).getId();
        emailUtils.sendSimpleEmail(email, "Reset password token", token);

        return new ResponseBody<>(ResponseStatus.GET_VERIFY_TOKEN_SUCCESSFUL);
    }

    @Override
    @Transactional
    public ResponseBody<?> googleAuthenticate(GoogleAuthRequestDto requestDto, String systemKey) {
        requestDto.getFormatted();
        UserInfoResponseDto dto;
        try {
            dto = googleUtils.getUserInfo(requestDto.getAuthToken());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        Optional<Account> opt = accountRepository.findByUserEmail(dto.getEmail());
        String token = "";
        if (opt.isEmpty()) {
            User user = User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .build();

            Account account = Account.builder()
                    .username(dto.getUsername())
                    .isExpired(false)
                    .isEnabled(true)
                    .lockAccountExpiration(new Timestamp(System.currentTimeMillis() - 1))
                    .roles(requestDto.getRoles())
                    .createDate(new Date(System.currentTimeMillis()))
                    .user(user)
                    .systemKey(systemKey)
                    .build();

            user.setAccount(account);

            accountRepository.save(account);
            token = jwtUtils.generateToken(account.getUsername());
        } else {
            Account account = accountRepository.findByUserEmail(dto.getEmail()).get();
            token = jwtUtils.generateToken(account.getUsername());
        }

        return new ResponseBody<>(ResponseStatus.AUTHENTICATION_SUCCESSFUL, new OauthAuthenticationResponseDto(token));
    }

    @Override
    @Transactional
    public ResponseBody<?> changePassword(String accountId, ChangePasswordRequestDto requestDto) {
        Account account = accountRepository.findById(accountId).get();
        if (!account.isEnabled()) {
            throw new DisabledException("");
        }
        if (!account.isAccountNonExpired()) {
            throw new AccountExpiredException("");
        }
        if (!account.isAccountNonLocked()) {
            throw new LockedException("");
        }
        List<String> violations = validationUtils.getViolationMessage(requestDto);
        if (!violations.isEmpty()) {
            try {
                String message = (new ObjectMapper()).writeValueAsString(violations);
                throw new ResetPasswordInformationConstraintViolateException(message);
            } catch (JsonProcessingException ignored) {
            }
        }
        account.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        accountRepository.save(account);
        return new ResponseBody<>(ResponseStatus.RESET_PASSWORD_SUCCESSFUL);
    }


}
