package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.repository.ListRepository;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private ListService listService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void findTaskByIdOrTitle_NullArguments() {
        ResponseEntity responseEntity = taskService.findTaskByIdOrTitle(null, null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode(), "Task is not returned correctly");
        assertEquals(null, responseEntity.getBody(), "Wrong value");
    }

    @Test
    void findTaskByIdOrTitle_ValidId() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        ResponseEntity responseEntity = taskService.findTaskByIdOrTitle(task.getId(), null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Task is not returned correctly");
        assertEquals(task, responseEntity.getBody(), "Wrong value");

        verify(taskRepository).findById(task.getId());
    }

    @Test
    void findTaskByIdOrTitle_ValidTitle() {
        Task task = new Task();
        task.setTitle("Sample task");

        when(taskRepository.findByTitle(task.getTitle())).thenReturn(task);

        ResponseEntity responseEntity = taskService.findTaskByIdOrTitle(null, task.getTitle());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Task is not returned correctly");
        assertEquals(task, responseEntity.getBody(), "Wrong value");

        verify(taskRepository).findByTitle(task.getTitle());
    }

    @Test
    void testCreateTask_ValidList() {
        Board board = new Board(1, "Sample Board");
        Group18.Demo.Trello.model.List list = new Group18.Demo.Trello.model.List(board, "sample");
        list.setId(1);
        List<Task> tasks = new ArrayList<>();
        Task task = new Task("Sample Task", null, null, list);
        tasks.add(task);

        when(listService.getList(list.getId())).thenReturn(list);

        ResponseEntity<Integer> response = taskService.createTask(list);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateTask_InvalidList() {
        Board board = new Board(1, "Sample Board");
        Group18.Demo.Trello.model.List list = new Group18.Demo.Trello.model.List(board, "sample");
        list.setId(1);
        List<Task> tasks = new ArrayList<>();
        Task task = new Task("Sample Task", null, null, list);
        tasks.add(task);

        when(listService.getList(list.getId())).thenReturn(null);

        ResponseEntity<Integer> response = taskService.createTask(list);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void assignMembersToTask_ValidTask() {
        Task task = new Task();
        task.setId(1);

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

    @Test
    void assignMembersToTask_InternalServerError() {
        Task task = new Task();
        task.setId(69);


        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        ResponseEntity responseEntity = taskService.assignMembersToTask(task);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error", responseEntity.getBody());

        verify(taskRepository).findById(task.getId());
    }

    @Test
    void testGetTask_ValidTask() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Task actualTask = taskService.getTask(1);
        assertNotNull(actualTask);
        assertEquals(task, actualTask);

        verify(taskRepository).findById(1);
    }

    @Test
    void testGetTask_InternalServerError() {
        int taskId = 69;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> taskService.getTask(taskId));

        verify(taskRepository).findById(taskId);
    }

    @Test
    void saveTask_ValidTask() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.save(task)).thenReturn(task);

        String result = taskService.saveTask(task);
        assertEquals("Task successfully saved", result);

        verify(taskRepository).save(task);
    }

    @Test
    void saveTask_InternalServerError() {
        Task task = new Task();
        task.setId(1);

        doThrow(new RuntimeException("Some database error")).when(taskRepository).save(task);

        String result = taskService.saveTask(task);
        assertEquals("Error: Unable to save task", result);

        verify(taskRepository).save(task);
    }
}