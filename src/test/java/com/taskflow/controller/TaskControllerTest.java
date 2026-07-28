package com.taskflow.controller;

import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void create_withValidBody_shouldReturn201() throws Exception {
        String requestBody = """
            {
             "title": "Title",
             "status": "TODO"
            }
            """;

        Project project = new Project();
        project.setId(1L);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Title");
        task.setStatus(TaskStatus.TODO);
        task.setProject(project);

        when(taskService.create(eq(1L), any())).thenReturn(task);

        mockMvc.perform(post("/api/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void create_withoutTitle_shouldReturn400() throws Exception {
        String requestBody = """
            {
             "status": "TODO"
            }
            """;

        mockMvc.perform(post("/api/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void findById_withInexistentId_shouldReturn404() throws Exception {
        when(taskService.findById(anyLong(), anyLong())).thenThrow(new ResourceNotFoundException("..."));

        mockMvc.perform(get("/api/projects/1/tasks/1"))
                .andExpect(status().isNotFound());
    }
}