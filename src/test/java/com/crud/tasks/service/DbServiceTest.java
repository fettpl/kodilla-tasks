package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {

    @Autowired
    DbService dbService;

    @Test
    public void getAllTasks() {
        // Given
        Task newTask = new Task(1L, "Task #1","Content");
        dbService.saveTask(newTask);

        // When
        List<Task> newDbTaskList = dbService.getAllTasks();

        // Then
        assertNotNull(newDbTaskList);
        assertEquals(1, newDbTaskList.size());
        assertEquals("Task #1", newDbTaskList.get(0).getTitle());
        assertEquals("Content", newDbTaskList.get(0).getContent());
        assertEquals(1L, newDbTaskList.get(0).getId(), 0);

        // Clean Up
        dbService.deleteTask(1L);
    }

    @Test
    public void testGetAllTaskFromEmptyList() {
        // Given
        DbService dbService = new DbService();

        // When
        List<Task> newDbTaskList = dbService.getAllTasks();

        // Then
        assertEquals(0, newDbTaskList.size());
    }


    @Test
    public void getTask() {
        // Given
        Task newTask = new Task(1L, "Task #1","Content");
        dbService.saveTask(newTask);

        // When
        Optional<Task> receivedTask = dbService.getTask(1L);

        // Then
        assertEquals(new Task(1L, "Task #1","Content"), receivedTask);

        // Clean Up
        dbService.deleteTask(1L);
    }

    @Test
    public void saveTask() {
        // Given
        Task newTask = new Task(1L, "Task #1","Content");
        Task newTask2 = new Task(2L, "Task #2","Content");
        dbService.saveTask(newTask);
        dbService.saveTask(newTask2);

        // When
        Optional<Task> receivedTask2 = dbService.getTask(2L);

        // Then
        assertEquals(new Task(1L, "Task #2","Content"), receivedTask2);

        // Clean Up
        dbService.deleteTask(1L);
        dbService.deleteTask(2L);
    }

    @Test
    public void deleteTask() {
        // Given
        Task newTask = new Task(1L, "Task #1","Content");
        dbService.saveTask(newTask);

        // When
        List<Task> newDbTaskList = dbService.getAllTasks();
        dbService.deleteTask(1L);

        // Then
        assertEquals(0, newDbTaskList.size());
    }
}