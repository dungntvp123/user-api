package com.microservice.user.repository;

import com.microservice.user.data.entity.ActionLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionLogRepository extends MongoRepository<ActionLog, String> {
}
