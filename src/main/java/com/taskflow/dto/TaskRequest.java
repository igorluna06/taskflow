package com.taskflow.dto;

import com.taskflow.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class TaskRequest {

    @NotBlank(message = "O título da tarefa é obrigatório")
    private String title;

    private String description;

    // status é obrigatório mesmo no create, embora seja ignorado nesse fluxo
    // (toda task nasce TODO). Considerar split de DTO se isso incomodar o frontend.
    @NotNull(message = "O status da tarefa é obrigatório")
    private TaskStatus status;

    private Long assigneeId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }

}
