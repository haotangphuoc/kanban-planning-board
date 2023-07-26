package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.UserRepository;
import Group18.Demo.Trello.service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void validatePassword() {
        String lower = "password";
        String lowerUpper = "Password";
        String lowerUpperNumber = "Password1";
        String validatedPassword = "Password1@";

        assertFalse(this.userService.validatePassword(lower), "Password should not be valid");
        assertFalse(this.userService.validatePassword(lowerUpper), "Password should not be valid");
        assertFalse(this.userService.validatePassword(lowerUpperNumber), "Password should not be valid");
        assertTrue(this.userService.validatePassword(validatedPassword), "Password should be valid");
    }

    @Test
    void signup_ExistingEmail() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Password1@");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        ResponseEntity<String> responseEntity = userService.signup(user);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Bad request due to existing email!");
        assertEquals("Email is already taken!", responseEntity.getBody(), "Wrong message");

        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void signup_WeakPassword() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("weakpassword");

        ResponseEntity<String> responseEntity = userService.signup(user);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Bad request due to weak password");
        assertEquals("Password is too weak!", responseEntity.getBody(), "Wrong message");
    }

    @Test
    void signup_ValidInformation() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Password1@");

        ResponseEntity<String> responseEntity = userService.signup(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "User should be sign up!");
        assertEquals("User registered successfully", responseEntity.getBody(), "Wrong message");
    }


    @Test
    void login_CorrectCredentials() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Password1@");

        when(userRepository.findByEmailAndPassword("user@email.com", "Password1@")).thenReturn(user);

        ResponseEntity responseEntity = this.userService.login(user);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Mismatch in response code");
        assertEquals("User login successfully!", responseEntity.getBody(), "Wrong message");

        verify(userRepository).findByEmailAndPassword("user@email.com", "Password1@");
    }

    @Test
    void login_WrongCredentials() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Wrongpassword1@");

        ResponseEntity responseEntity = this.userService.login(user);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Mismatch in response code");
        assertEquals("Invalid email or password!", responseEntity.getBody(), "Wrong message");
    }

    @Test
    void resetPassword_InvalidEmail() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Password1@");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        ResponseEntity<String> responseEntity = userService.resetPassword(user);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Bad request due to invalid email!");
        assertEquals("Can't find user object!", responseEntity.getBody(), "Wrong message");

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void resetPassword_IncorrectAnswer() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Password1@");
        user.setQuestionAns("FalseAnswer");

        User userDB = new User();
        userDB.setEmail(user.getEmail());
        userDB.setQuestionAns("CorrectAnswer");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(userDB);

        ResponseEntity<String> responseEntity = userService.resetPassword(user);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Your answer is not correct!", responseEntity.getBody(), "Wrong message");

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void resetPassword_CorrectInfo() {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("Password1@");
        user.setQuestionAns("CorrectAnswer");

        User userInDb = new User();
        userInDb.setEmail(user.getEmail());
        userInDb.setQuestionAns(user.getQuestionAns());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(userInDb);

        ResponseEntity<String> responseEntity = userService.resetPassword(user);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Reset password successfully!", responseEntity.getBody(), "Wrong message");

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void getUserIdByEmailTest_CorrectEmail() {
        String email = "emailexists@email.com";
        User user = new User();
        user.setId(1);

        when(userRepository.findByEmail(email)).thenReturn(user);

        ResponseEntity responseEntity = userService.findUserIdByEmail(email);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "User Id shoule be found!");
        assertEquals(1, responseEntity.getBody(), "Wrong message");

        verify(userRepository).findByEmail(email);
    }

    @Test
    void getUserIdByEmail_NonExistingEmail() {
        String email = "emailnonexists@email.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        ResponseEntity responseEntity = userService.findUserIdByEmail(email);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "User Id shoule be found!");
        assertEquals(-1, responseEntity.getBody(), "Wrong message");

        verify(userRepository).findByEmail(email);
    }

    @Test
    void getUser_ValidUserId() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setEmail("sample@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("sample@gmail.com", result.getEmail());

        verify(userRepository).findById(userId);
    }

    @Test
    void getUser_InternalServerError() {
        int userId = 69;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> userService.getUser(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setEmail("sample@gmail.com");

        when(userRepository.save(user)).thenReturn(user);

        String result = userService.saveUser(user);
        assertEquals("User successfully saved", result);

        verify(userRepository).save(user);
    }

    @Test
    void fetchWorkspaceById() {
        int userId = 1;

        User user = new User();
        user.setId(userId);
        List<Workspace> workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1, "Workspace 1", "description"));
        workspaces.add(new Workspace(2, "Workspace 2", "description"));
        user.setWorkspaces(workspaces);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity response = userService.fetchWorkspaceById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(workspaces, response.getBody());
    }

    @Test
    void fetchBoardById() {
        int userId = 1;

        User user = new User();
        user.setId(userId);
        List<Board> boards = new ArrayList<>();
        boards.add(new Board(1, "Board 1"));
        boards.add(new Board(2, "Board 2"));
        user.setBoards(boards);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity response = userService.fetchBoardById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(boards, response.getBody());
    }

    @Test
    void fetchTaskById() {
        int userId = 1;

        User user = new User();
        user.setId(userId);
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1", null, null, null));
        tasks.add(new Task("Task 2", null, null, null));
        user.setTasks(tasks);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity response = userService.fetchTaskById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void findByEmailAndPassword() {
        User user = new User();
        user.setEmail("sample@gmail.com");
        user.setPassword("Password123@#");

        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);

        User result = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setEmail("sample@gmail.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User result = userService.findByEmail(user.getEmail());
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void existsByEmail() {
        User user = new User();
        user.setEmail("sample@gmail.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        Boolean result = userService.existsByEmail(user.getEmail());
        assertNotNull(result);
        assertEquals(true, result);
    }
}