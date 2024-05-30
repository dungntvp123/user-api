package com.microservice.user.data.dto.response;

import com.microservice.user.config.SystemConfig;
import com.microservice.user.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadUserResponseDto {
    private Map<String, Object> data = new HashMap<>();

    public LoadUserResponseDto getBrief(User user) {
        data.put("user", SystemConfig.USER_URL + "/" + user.getId());
        data.put("name", user.getName());
        return this;
    }
    public LoadUserResponseDto getDetail(User user) {
        data.put("user_id", user.getId());
        data.put("account", SystemConfig.ACCOUNT_URL + "/" + user.getAccount().getId());
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("address", user.getAddress());
        data.put("phone", user.getPhone());
        data.put("bio", user.getBio());
        data.put("image", SystemConfig.IMAGE_URL + "/" + user.getImage());
        return this;
    }
}
