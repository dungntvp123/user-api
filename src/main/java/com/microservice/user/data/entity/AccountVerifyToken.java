package com.microservice.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
@Builder
public class AccountVerifyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Timestamp expiration;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    public AccountVerifyToken(Account account) {
        this.account = account;
        this.expiration = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
    }

    public AccountVerifyToken() {
        this.expiration = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
    }

    public boolean isExpired() {
        return new Timestamp(System.currentTimeMillis()).after(expiration);
    }
}
