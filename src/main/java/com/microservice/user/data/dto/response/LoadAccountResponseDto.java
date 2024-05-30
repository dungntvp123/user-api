package com.microservice.user.data.dto.response;

import com.microservice.user.config.SystemConfig;
import com.microservice.user.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadAccountResponseDto {
    private Map<String, Object> data = new HashMap<>();

    public LoadAccountResponseDto getDetail(Account account) {
        data.put("id", account.getId());
        data.put("username", account.getUsername());
        data.put("user", SystemConfig.USER_URL + "/" + account.getUser().getId());
        data.put("isEnable", account.isEnabled());
        data.put("lockAccountExpiration", account.getLockAccountExpiration());
        data.put("isLock", !account.isAccountNonLocked());
        data.put("createDate", account.getCreateDate());
        data.put("settings", account.getSettings());
        data.put("isExpired", account.isExpired());
        data.put("roles", account.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return this;
    }

    public LoadAccountResponseDto getBrief(Account account) {
        data.put("account", SystemConfig.ACCOUNT_URL + "/" + account.getId());
        data.put("username", account.getUsername());
        data.put("isEnable", account.isEnabled());
        data.put("isLock", !account.isAccountNonLocked());
        data.put("settings", account.getSettings());
        data.put("isExpired", account.isExpired());
        return this;
    }
}
