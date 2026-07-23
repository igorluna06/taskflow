package com.taskflow.service;

import com.taskflow.dto.TaskRequest;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.model.User;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
    UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<Task> findAllByProjectId(Long projectId, TaskStatus status) {
        return taskRepository.findByProjectIdAndOptionalStatus(projectId,  status);
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
        task.setAssignee(resolveAssignee(request.getAssigneeId()));
        return this.taskRepository.save(task);
    }

    public Task update(Long projectId,Long taskId, TaskRequest request) {
        Task task = this.findById(projectId,taskId);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        User assignee = resolveAssignee(request.getAssigneeId());
        if(assignee != null){
            task.setAssignee(assignee);
        }
        return this.taskRepository.save(task);
    }

    public void delete(Long projectId, Long taskId) {
        Task task = this.findById(projectId,taskId);
        this.taskRepository.delete(task);
    }

    private User resolveAssignee(Long assigneeId){
        if(assigneeId == null){
            return null;
        }

        return this.userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id " + assigneeId));
    }


}
