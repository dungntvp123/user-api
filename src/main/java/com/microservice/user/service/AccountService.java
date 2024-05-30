package com.microservice.user.service;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.data.dto.request.*;
import com.microservice.user.specification.AccountCriteria;

public interface AccountService extends AuthService, AccountCriteria {
    public ResponseBody<?> changePassword(String accountId,ChangePasswordRequestDto requestDto);
    public ResponseBody<?> register(RegisterRequestDto requestDto, String systemKey);
    public ResponseBody<?> registerAlt(RegisterRequestDto requestDto, String systemKey);
    public ResponseBody<?> lock(String accountId,LockAccountRequestDto requestDto);
    public ResponseBody<?> delete(String accountId);
    public ResponseBody<?> restore(String accountId);
    public ResponseBody<?> loadAccount(LoadAccountListRequestDto requestDto);
    public ResponseBody<?> loadAccount(String accountId);
}
