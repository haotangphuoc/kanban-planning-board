package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else {
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasLowercase && hasNumber && hasSpecialChar;
    }

    public ResponseEntity<String> signup(User user) {
        try {
            if(existsByEmail(user.getEmail())){
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }
            if(!validatePassword(user.getPassword())){
                return new ResponseEntity<>("Password is too weak!", HttpStatus.BAD_REQUEST);
            }

            saveUser(new User(user.getEmail(), user.getPassword(), user.getQuestionAns(), user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> login(User user) {
        try {
            User userRes = findByEmailAndPassword(user.getEmail(),user.getPassword());
            if(userRes==null){
                return new ResponseEntity<>("Invalid email or password!", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("User login successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> resetPassword(User user) {
        try {
            User userInDb = findByEmail(user.getEmail());
            if(userInDb==null){
                return new ResponseEntity<>("Can't find user object!", HttpStatus.BAD_REQUEST);
            }
            if(!user.getQuestionAns().equals(userInDb.getQuestionAns())){
                return new ResponseEntity<>("Your answer is not correct!", HttpStatus.BAD_REQUEST);
            }
            userInDb.setPassword(user.getPassword());
            saveUser(userInDb);
            return new ResponseEntity<>("Reset password successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> findUserIdByEmail(String email) {
        try {
            User user = findByEmail(email);
            if (user == null) {
                return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User getUser(int userId) {
        User user = userRepository.findById(userId).get();
        return user;
    }

    public String saveUser(User user) {
        userRepository.save(user);
        return "User successfully saved";
    }

    public String deleteUser(int userId) {
        userRepository.deleteById(userId);
        return "User successfully deleted";
    }

    public String updateUser(int userId, User newUser) {
        User oldUser = userRepository.findById(userId).get();

        if(Objects.nonNull(newUser.getEmail()) &&
                !"".equalsIgnoreCase(newUser.getEmail())) {
            oldUser.setEmail(newUser.getEmail());
        }

        if(Objects.nonNull(newUser.getQuestionAns()) &&
                !"".equalsIgnoreCase(newUser.getQuestionAns())) {
            oldUser.setQuestionAns(newUser.getQuestionAns());
        }

        if(Objects.nonNull(newUser.getFirstName()) &&
                !"".equalsIgnoreCase(newUser.getFirstName())) {
            oldUser.setFirstName(newUser.getFirstName());
        }

        if(Objects.nonNull(newUser.getLastName()) &&
                !"".equalsIgnoreCase(newUser.getLastName())) {
            oldUser.setLastName(newUser.getLastName());
        }

        userRepository.save(oldUser);
        return "User successfully updated";
    }

    public List<User> fetchUserList() {
        return userRepository.findAll();
    }

    public List<Workspace> fetchWorkspaceById(int userId) {
        User user = userRepository.findById(userId).get();
        return user.getWorkspaces();
    }

    public List<Board> fetchBoardById(int userId) {
        User user = userRepository.findById(userId).get();
        return user.getBoards();
    }

    public List<Task> fetchTaskById(int userId) {
        User user = userRepository.findById(userId).get();
        return user.getTasks();
    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
