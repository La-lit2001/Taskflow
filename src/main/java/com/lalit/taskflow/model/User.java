package com.lalit.taskflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public User() {}
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId()                    { return id; }
    public String getUsername()            { return username; }
    public void setUsername(String u)      { this.username = u; }
    public String getEmail()               { return email; }
    public void setEmail(String e)         { this.email = e; }
    public String getPassword()            { return password; }
    public void setPassword(String p)      { this.password = p; }
    public LocalDateTime getCreatedAt()    { return createdAt; }
}
