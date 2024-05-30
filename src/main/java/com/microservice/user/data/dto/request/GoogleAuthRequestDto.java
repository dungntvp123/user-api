package com.microservice.user.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleAuthRequestDto {
    private String authToken;
    private String roles;

    public void getFormatted() {
        authToken = authToken == null ? "" : authToken;
        roles = roles == null ? "" : roles;
    }
}
