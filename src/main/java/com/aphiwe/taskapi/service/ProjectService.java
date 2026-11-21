package com.aphiwe.taskapi.service;

import com.aphiwe.taskapi.dto.ProjectRequest;
import com.aphiwe.taskapi.dto.ProjectResponse;
import com.aphiwe.taskapi.entity.Project;
import com.aphiwe.taskapi.exception.ResourceNotFoundException;
import com.aphiwe.taskapi.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = findProjectOrThrow(id);
        return toResponse(project);
    }

    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = findProjectOrThrow(id);
        project.setName(request.getName());
        project.setDescription(request.getDescription());

        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = findProjectOrThrow(id);
        projectRepository.delete(project);
    }

    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt()
        );
    }
}