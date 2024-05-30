package com.microservice.user.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;
    @Column(unique = true)
    private String email;
    private String name;
    private String address;
    private String phone;
    private String image;
    private String bio;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "friend",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "has_friend"))
    private List<User> friends = new ArrayList<>();
    @Column(columnDefinition = "TEXT")
    private String others;


}
