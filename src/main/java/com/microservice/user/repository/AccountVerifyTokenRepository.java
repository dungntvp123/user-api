package com.microservice.user.repository;

import com.microservice.user.data.entity.AccountVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountVerifyTokenRepository extends JpaRepository<AccountVerifyToken, String> {
    AccountVerifyToken findByAccountId(String id);
}
