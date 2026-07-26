package com.taskflow.task;

import com.taskflow.dto.TaskRequest;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.model.User;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void update_withoutAssigneeId_shouldPreserveCurrentAssignee(){
        Project project = new Project();
        project.setId(1L);
        User user = new User();
        Task task = new Task();
        task.setId(1L);
        task.setAssignee(user);
        task.setProject(project);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("title");
        taskRequest.setDescription("description");
        taskRequest.setStatus(TaskStatus.TODO);

        Task result = taskService.update(1L, 1L, taskRequest);

        assertThat(result.getAssignee()).isEqualTo(user);
    }

    @Test
    public void findById_projectIdDoesNotMatchTask_shouldThrowResourceNotFoundException() {
        Project project = new Project();
        project.setId(1L);

        User user = new User();
        user.setId(1L);

        Task task = new Task();
        task.setId(1L);
        task.setAssignee(user);
        task.setProject(project);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.findById(2L, 1L);
        });
    }

    @Test
    public void create_withValidData_shouldCreateTaskSuccessfully() {
        Project project = new Project();
        project.setId(1L);

        User user = new User();
        user.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskRequest request = new TaskRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAssigneeId(user.getId());

        Task result = taskService.create(1L, request);

        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getDescription()).isEqualTo("description");
        assertThat(result.getProject()).isEqualTo(project);
        assertThat(result.getAssignee()).isEqualTo(user);
        assertThat(result.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    public void create_projectDoesNotExist_shouldThrowResourceNotFoundException() {
        TaskRequest request = new TaskRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAssigneeId(1L);

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.create(2L, request);
        });
    }

    @Test
    public void create_assigneeDoesNotExist_shouldThrowResourceNotFoundException(){
        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        TaskRequest request = new TaskRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAssigneeId(1L);

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.create(1L, request);
        });
    }

    @Test
    public void update_assigneeIdDoesNotExist_shouldThrowResourceNotFoundException() {
        Project project = new Project();
        project.setId(1L);

        Task task = new Task();
        task.setId(1L);
        task.setProject(project);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskRequest request = new TaskRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAssigneeId(1L);

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.update(1L, 1L, request);
        });
    }

    @Test
    public void update_withValidAssigneeId_shouldUpdateAssignee(){
        Project project = new Project();
        project.setId(1L);

        User user = new User();
        user.setId(1L);

        Task task = new Task();
        task.setId(1L);
        task.setProject(project);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskRequest request = new TaskRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setAssigneeId(user.getId());

        Task result = taskService.update(1L, 1L, request);

        assertThat(result.getAssignee()).isEqualTo(user);
    }

    @Test
    public void findById_taskDoesNotExist_shouldThrowResourceNotFoundException(){
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.findById(1L, 1L);
        });
    }

}
