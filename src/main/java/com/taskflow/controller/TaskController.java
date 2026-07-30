package com.taskflow.controller;

import com.taskflow.dto.TaskRequest;
import com.taskflow.dto.TaskResponse;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasksByProjectId(@PathVariable Long projectId, @RequestParam(required = false) TaskStatus status) {
        List<Task> tasks = this.taskService.findAllByProjectId(projectId,  status);

        return tasks.stream().map(this::toResponse).toList();}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@PathVariable Long projectId, @Valid @RequestBody TaskRequest request) {
        return this.toResponse(this.taskService.create(projectId,request));
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return this.toResponse(this.taskService.findById(projectId,taskId));
    }

    @PutMapping("/{taskId}")
    public TaskResponse update(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
        return this.toResponse(this.taskService.update(projectId,taskId, request));
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateStatus(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskStatus status) {
        return this.toResponse(this.taskService.updateStatus(projectId,taskId,status));
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long projectId, @PathVariable Long taskId) {
        this.taskService.delete(projectId,taskId);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getProject().getId(),
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getAssignee() != null ? task.getAssignee().getId() : null,
                task.getDueDate()
        );
    }
}
