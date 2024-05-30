package com.microservice.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private User user;
    private boolean isExpired;
    private boolean isEnabled;
    private Timestamp lockAccountExpiration;
    @Column(columnDefinition = "TEXT")
    private String settings;
    private String roles;
    private Date createDate;
    private String systemKey;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return null;
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles.split(",")) {
            grantedAuthorities.add((GrantedAuthority) role::trim);
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (lockAccountExpiration == null) {
            return true;
        }
        return lockAccountExpiration.before(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
