package com.aphiwe.taskapi.repository;

import com.aphiwe.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}