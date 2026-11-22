package com.aphiwe.taskapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;
  
  private String description;
  
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<Task> tasks = new ArrayList<>();

  public Project(){  
  }

  @PrePersist
  protected void onCreate() {
      this.createdAt = LocalDateTime.now();
  }

  public LocalDateTime getCreatedAt(){
    return this.createdAt;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public String getDescription(){
    return this.description;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setId(Long id){
    this.id = id;
  }

  public Long getId(){
    return this.id;
  }
  
}