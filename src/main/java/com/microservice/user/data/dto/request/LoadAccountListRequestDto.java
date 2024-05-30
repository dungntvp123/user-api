package com.microservice.user.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadAccountListRequestDto {
    private String username;
    private String email;
    private String orderByUsername;
    private String orderByCreateDate;
    private Integer pageIndex;

    public void getFormatted() {
        username = username == null ? "" : username;
        email = email == null ? "" : email;
        pageIndex = pageIndex == null ? 1 : pageIndex;
    }
}
