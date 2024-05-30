package com.microservice.user.repository;

import com.microservice.user.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByUserEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByUserEmail(String username);
}
