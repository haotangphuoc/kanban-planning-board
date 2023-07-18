package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.repository.UserRepository;
import Group18.Demo.Trello.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void getUserIdByEmail_WithNonExistingEmail_ShouldReturnBadRequest() {
        // Arrange
        String email = "emailnonexists@email.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act
        ResponseEntity responseEntity = userService.findUserIdByEmail(email);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "User Id shoule be found!");
        assertEquals(-1, responseEntity.getBody(), "Wrong message");

        // Verify that findByEmail method was called
        verify(userRepository).findByEmail(email);
    }
}