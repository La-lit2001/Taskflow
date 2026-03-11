package com.lalit.taskflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String status = "TODO";      // TODO | IN_PROGRESS | DONE

    @Column
    private String priority = "MEDIUM";  // LOW | MEDIUM | HIGH

    @Column
    private String assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist  protected void onCreate() { createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate   protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public Task() {}

    public Long getId()                    { return id; }
    public String getTitle()               { return title; }
    public void setTitle(String t)         { this.title = t; }
    public String getDescription()         { return description; }
    public void setDescription(String d)   { this.description = d; }
    public String getStatus()              { return status; }
    public void setStatus(String s)        { this.status = s; }
    public String getPriority()            { return priority; }
    public void setPriority(String p)      { this.priority = p; }
    public String getAssignee()            { return assignee; }
    public void setAssignee(String a)      { this.assignee = a; }
    public User getUser()                  { return user; }
    public void setUser(User u)            { this.user = u; }
    public LocalDateTime getCreatedAt()    { return createdAt; }
    public LocalDateTime getUpdatedAt()    { return updatedAt; }
}
