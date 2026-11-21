package com.aphiwe.taskapi.dto;

import java.time.LocalDateTime;

public class ProjectResponse {

  private Long id;
  private String name;
  private String description;
  private LocalDateTime createdAt;

  public ProjectResponse(){
  }

  public ProjectResponse(Long id, String name, String description, LocalDateTime createdAt) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.createdAt = createdAt;
  }

  public void setId(Long id){
    this.id = id;
  }

  public Long getId(){
    return this.id;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public String getDescription(){
    return this.description;
  }

  public void setCreatedAt(LocalDateTime createdAt){
    this.createdAt = createdAt;
  }

  public LocalDateTime getCreatedAt(){
    return this.createdAt;
  }
}