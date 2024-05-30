package com.microservice.user.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class UsernamePasswordAuthRequestDto {
    private String username;
    private String password;
}
