package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void shouldFetchEmptyTasks() throws Exception {
        // Given
        List<Task> taskList = new ArrayList<>();
        List<TaskDto> taskDtoListList = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(Mockito.anyList())).thenReturn(taskDtoListList);

        // When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or isOk
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    public void shouldFetchTasks() throws Exception {
        List<TaskDto> dtos = new ArrayList<>();
        dtos.add(new TaskDto((long) 1, "Task #1", "Content"));
        dtos.add(new TaskDto((long) 2, "Task #2", "Content"));
        dtos.add(new TaskDto((long) 3, "Task #3", "Content"));

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(dtos);

        // When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) // or isOk
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].title", is("Task #1")))
                .andExpect(jsonPath("$[0].content", is("Content")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto((long) 1, "taskDto", "contentDto");
        Task task = new Task((long) 1, "Task #1", "Content");

        when(dbService.getTask(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1"))
                .andExpect(status().is(200));

        verify(dbService, times(1)).deleteTask(any());
    }


    @Test
    public void testUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto((long) 1, "taskDto", "contentDto");
        Task task = new Task((long) 1, "Task #1", "Content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);
        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/task/updateTask?taskId=1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto((long) 1, "taskDto", "contentDto");
        Task task = new Task((long) 1, "Task #1", "Content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(taskMapper.mapToTask(taskDto))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}