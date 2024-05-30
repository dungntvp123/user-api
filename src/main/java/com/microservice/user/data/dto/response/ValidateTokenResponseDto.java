package com.microservice.user.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateTokenResponseDto {
    private boolean isValid;
    private String accessToken;
    private LoadAccountResponseDto dto = new LoadAccountResponseDto();

    public ValidateTokenResponseDto(boolean isValid) {
        this.isValid = isValid;
    }
}
