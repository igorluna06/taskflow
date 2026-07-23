package com.taskflow.repository;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND (:status IS NULL OR t.status = :status)")
    List<Task> findByProjectIdAndOptionalStatus(@Param("projectId") Long projectId, @Param("status") TaskStatus status);
}
