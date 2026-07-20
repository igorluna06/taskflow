package com.taskflow.service;

import com.taskflow.dto.ProjectRequest;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.User;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado com id " + id));
    }

    public Project create(ProjectRequest request) {
        User owner = null;
        if (request.getOwnerId() != null) {
            owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id " + request.getOwnerId()));
        }
        Project project = new Project(request.getName(), request.getDescription(), owner);
        return projectRepository.save(project);
    }

    public Project update(Long id, ProjectRequest request) {
        Project project = findById(id);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        Project project = findById(id);
        projectRepository.delete(project);
    }
}
