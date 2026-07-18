package com.linkpulse.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
@Id
@GeneratedValue(strategy =  GenerationType.IDENTITY)
private Long id;


@Column(nullable = false)
    private String name;
@Column(nullable = false,unique = true)
    private String email;
@Column(nullable = false)
    private String password;
@Enumerated(EnumType.STRING)
    private Role role;

private String profileImageUrl;
private boolean isVerified;
private LocalDateTime createdAt;
private LocalDateTime updatedAt;

}
