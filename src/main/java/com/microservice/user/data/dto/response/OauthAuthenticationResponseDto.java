package com.microservice.user.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OauthAuthenticationResponseDto {
    private String token;
}
