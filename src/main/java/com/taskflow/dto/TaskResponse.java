package com.taskflow.dto;

import com.taskflow.model.TaskStatus;

public class TaskResponse {

    private final Long projectId;
    private final Long taskId;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final Long assigneeId;


    public TaskResponse(Long projectId, Long taskId, String title, String description,
                        TaskStatus status, Long assigneeId) {
        this.projectId = projectId;
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assigneeId = assigneeId;
    }

    public Long getProjectId() { return projectId; }
    public Long getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Long getAssigneeId() { return assigneeId; }
}
