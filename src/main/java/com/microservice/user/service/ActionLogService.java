package com.microservice.user.service;

import com.microservice.user.common.EventType;

public interface ActionLogService {
    void logAction(String userId, EventType eventType, String desc, String status);
}
