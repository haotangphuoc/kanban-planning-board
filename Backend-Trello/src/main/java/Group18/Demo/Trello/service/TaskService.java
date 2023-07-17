package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Task;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity<Task> findTaskByIdOrTitle(Integer id, String title) {
        return null;
    }

    public ResponseEntity<String> modifyTask(Task task) {
        return null;
    }

    public ResponseEntity<String> createTask(Task task) {
        return null;
    }

    public ResponseEntity<String> assignMembersToTask(@RequestBody Task task) {
        return null;
    }


    public Task getTask(int taskId) {
        Task task = taskRepository.findById(taskId).get();
        return task;
    }

    public String saveTask(Task task) {
        taskRepository.save(task);
        return "Task successfully saved";
    }

    public String deleteTask(int taskId) {
        taskRepository.deleteById(taskId);
        return "Task successfully deleted";
    }

    public String updateTask(int taskId, Group18.Demo.Trello.model.Task newTask) {
        Group18.Demo.Trello.model.Task oldTask = taskRepository.findById(taskId).get();

        if(Objects.nonNull(newTask.getTitle()) &&
                !"".equalsIgnoreCase(newTask.getTitle())) {
            oldTask.setTitle(newTask.getTitle());
        }

        if(Objects.nonNull(newTask.getDescription()) &&
                !"".equalsIgnoreCase(newTask.getDescription())) {
            oldTask.setDescription(newTask.getDescription());
        }

        oldTask.setActive(newTask.isActive());

        taskRepository.save(oldTask);
        return "Task successfully updated";
    }

    public List<Task> fetchTaskList() {
        return taskRepository.findAll();
    }

    public List<User> fetchUserById(int taskId) {
        Task task = taskRepository.findById(taskId).get();
        return task.getUsers();
    }
}
