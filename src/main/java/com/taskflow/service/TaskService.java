package com.taskflow.service;

import com.taskflow.dto.TaskRequest;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> findAllByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task findById(Long projectId, Long taskId) {
        Task task = this.taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com id" + taskId));

        if(!task.getProject().getId().equals(projectId)){
            throw new ResourceNotFoundException("Tarefa não encontrada com id " + taskId);
        }

        return task;
    }

    public Task create(Long projectId, TaskRequest request) {
        Project project = this.projectRepository.findById(projectId)
                    .orElseThrow(()  -> new ResourceNotFoundException("Projeto não encontrado com id "
                            + projectId));


        Task task = new Task(request.getTitle(), request.getDescription(), project);
        return this.taskRepository.save(task);
    }

    public Task update(Long projectId,Long taskId, TaskRequest request) {
        Task task = this.findById(projectId,taskId);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        return this.taskRepository.save(task);
    }

    public void delete(Long projectId, Long taskId) {
        Task task = this.findById(projectId,taskId);
        this.taskRepository.delete(task);
    }


}
