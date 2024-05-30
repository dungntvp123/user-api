package com.microservice.user.service.impl;

import com.microservice.user.common.EventType;
import com.microservice.user.data.entity.ActionLog;
import com.microservice.user.repository.ActionLogRepository;
import com.microservice.user.service.ActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ActionLogServiceImpl implements ActionLogService {
    @Autowired
    private ActionLogRepository actionLogRepository;

    @Override
    public void logAction(String userId, EventType eventType, String desc, String status) {
        ActionLog actionLog = ActionLog.builder()
                .timestamp(Instant.now().toString())
                .userId(userId)
                .eventType(eventType.name())
                .description(desc)
                .status(status)
                .ipAddress("127.0.0.1")
                .userAgent("Mozilla/5.0")
                .build();

        actionLogRepository.save(actionLog);
    }
}
