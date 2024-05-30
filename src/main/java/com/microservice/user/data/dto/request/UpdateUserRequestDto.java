package com.microservice.user.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDto {
    private String name;
    private String address;
    private String phone;
    private String bio;
}
