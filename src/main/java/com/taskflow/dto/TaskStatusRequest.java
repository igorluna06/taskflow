package com.taskflow.dto;

import com.taskflow.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class TaskStatusRequest {
    @NotNull(message = "O status é obrigatório")
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
