package com.microservice.user.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {
    public static final String HOST = "http://localhost:8080";
    public static final String ACCOUNT_URL = HOST + "/api/v1/accounts";
    public static final String USER_URL = HOST + "/api/v1/users";
    public static final String IMAGE_URL = HOST + "/api/v1/images";
    public static final String UPLOAD_FILE_DIRECTORY = "E:/Self-Project/Upload_File/user/";
}
