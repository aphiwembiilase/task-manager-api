package com.aphiwe.taskapi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.MEDIUM;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Task(){
    }

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate(){
        return this.dueDate;
    }

    public void setPriority(TaskPriority priority){
        this.priority = priority;
    }

    public TaskPriority getPriority(){
        return this.priority;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public TaskStatus getStatus(){
        return this.status;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public Project getProject(){
        return this.project;
    }

    public void setProject(Project project){
        this.project = project;
    }

}