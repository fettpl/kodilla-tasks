package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "taskDto", "content");

        // When
        Task task = taskMapper.mapToTask(taskDto);

        // Then
        assertEquals(1L, task.getId(), 0);
    }

    @Test
    public void testMapToTaskDto() {
        // Given
        Task task = new Task(1L, "task", "content");

        // When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // Then
        assertEquals(1L, taskDto.getId(), 0);
    }

    @Test
    public void testMapToTaskDtoList() {
        // Given
        Task task = new Task(1L, "task", "content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        // When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        // Then
        assertEquals(1, taskDtos.size());
    }
}
