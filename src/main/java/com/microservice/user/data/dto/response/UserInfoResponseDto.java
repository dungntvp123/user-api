package com.microservice.user.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String name;
    private String image;
}
