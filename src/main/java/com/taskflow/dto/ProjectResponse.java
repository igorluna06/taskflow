package com.taskflow.dto;

import java.time.LocalDateTime;

public class ProjectResponse {

    private final Long projectId;
    private final String name;
    private final String description;
    private final Long ownerId;
    private final LocalDateTime createdAt;

    public ProjectResponse(Long projectId, String name, String description, Long ownerId,  LocalDateTime createdAt) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public Long getProjectId() {
        return projectId;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Long getOwnerId() {
        return ownerId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
