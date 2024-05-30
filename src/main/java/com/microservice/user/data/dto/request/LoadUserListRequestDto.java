package com.microservice.user.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadUserListRequestDto {
    private String name;
    private String email;
    private String phone;
    private Integer pageIndex;

    public void getFormatted() {
        name = name == null ? "" : name;
        email = email == null ? "" : email;
        phone = phone == null ? "" : phone;
        pageIndex = pageIndex == null ? 1 : pageIndex;
    }
}
