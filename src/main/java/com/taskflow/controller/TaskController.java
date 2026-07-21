package com.taskflow.controller;

import com.taskflow.dto.TaskRequest;
import com.taskflow.model.Task;
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
    public List<Task> getAllTasksByProjectId(@PathVariable Long projectId) {
        return this.taskService.findAllByProjectId(projectId);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@PathVariable Long projectId, @Valid @RequestBody TaskRequest request) {
        return this.taskService.create(projectId,request);
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return this.taskService.findById(projectId,taskId);
    }

    @PutMapping("/{taskId}")
    public Task update(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
        return this.taskService.update(projectId,taskId, request);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long projectId, @PathVariable Long taskId) {
        this.taskService.delete(projectId,taskId);
    }
}
