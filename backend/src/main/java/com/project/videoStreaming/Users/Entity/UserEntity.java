package com.project.videoStreaming.Users.Entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name="users")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userid", updatable = false, nullable = false)
    private UUID userId;

    @Column (name = "username")
    private String username;

    @Column (name = "firstname")
    private String firstname;

    @Column (name = "lastname")
    private String lastname;

    @Column (name = "email")
    private String email;

    @Column (name = "password")
    private String password;

    @Column(name = "subs_count")
    private int subs_count;

    @Column (name = "birthdate")
    private LocalDate birthdate;

    @Column (name = "profile")
    private String profile;
}
