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
    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String CLIENT_ID;
    public UserInfoResponseDto getUserInfo(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {

            GoogleIdToken.Payload payload = idToken.getPayload();
            return UserInfoResponseDto.builder()
                    .username(payload.getEmail().split("@")[0].trim())
                    .email(payload.getEmail())
                    .name((String) payload.get("name"))
                    .image((String) payload.get("picture"))
                    .build();
        }
        return null;
    }
}
