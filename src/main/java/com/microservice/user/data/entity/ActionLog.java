package com.microservice.user.data.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "action_logs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActionLog {
    @Id
    private String id;
    private String timestamp;
    private String userId;
    private String eventType;
    private String description;
    private String ipAddress;
    private String userAgent;
    private String status;
    private Map<String, Object> additionalData;
}
