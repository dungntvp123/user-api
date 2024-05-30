package com.microservice.user.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.microservice.user.data.dto.response.UserInfoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleUtils {
    private String CLIENT_ID;
    public UserInfoResponseDto getUserInfo(String idTokenString) throws GeneralSecurityException, IOException {

        return null;
    }
}
