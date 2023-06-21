package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

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
}
