package com.project.videoStreaming.Users.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID userId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int subs_count;
    private LocalDate birthdate;
    private String profile;
}