package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Test
    void findTaskByIdOrTitle_NullArguments() {
        ResponseEntity responseEntity = taskService.findTaskByIdOrTitle(null, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode(), "Task is not returned correctly");
        assertEquals(null, responseEntity.getBody(), "Wrong value");
    }

    @Test
    void findTaskByIdOrTitle_CorrectId() {
        Task task = new Task(1, "sample task", "description", true);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        ResponseEntity responseEntity = taskService.findTaskByIdOrTitle(task.getId(), null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Task is not returned correctly");
        assertEquals(task, responseEntity.getBody(), "Wrong value");

        verify(taskRepository).findById(task.getId());
    }

    @Test
    void findTaskByIdOrTitle_CorrectTitle() {
        Task task = new Task(1, "sample task", "description", true);

        when(taskRepository.findByTitle(task.getTitle())).thenReturn(task);

        ResponseEntity responseEntity = taskService.findTaskByIdOrTitle(null, task.getTitle());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Task is not returned correctly");
        assertEquals(task, responseEntity.getBody(), "Wrong value");

        verify(taskRepository).findByTitle(task.getTitle());
    }

    @Test
    void modifyTask() {
        Task task = new Task(1, "sample task", "description", true);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        ResponseEntity responseEntity = taskService.modifyTask(task);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Task should be modified");
        assertEquals("Modify task successfully!", responseEntity.getBody(), "Wrong message");

        verify(taskRepository).findById(task.getId());
        verify(taskRepository).save(task);
    }

    @Test
    void assignMembersToTask() {
        Task task = new Task(1, "sample task", "description", true);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        User user1 = new User();
        User user2 = new User();
        user1.setEmail("user1@example.com");
        user2.setEmail("user2@example.com");
        user1.setTasks(tasks);
        user2.setTasks(tasks);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        task.setUsers(users);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(userService.findByEmail(user1.getEmail())).thenReturn(user1);
        when(userService.findByEmail(user2.getEmail())).thenReturn(user2);

        ResponseEntity responseEntity = taskService.assignMembersToTask(task);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Assign members to task successfully!", responseEntity.getBody());

        verify(taskRepository).findById(task.getId());
        verify(userService).findByEmail(user1.getEmail());
        verify(userService).findByEmail(user2.getEmail());
        verify(userService).saveUser(user1);
        verify(userService).saveUser(user2);
    }
}