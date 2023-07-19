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
    @Autowired
    UserService userservice;


    public ResponseEntity<Task> findTaskByIdOrTitle(Integer id, String title) {
        try {
            Task taskInDb = null;
            if (id != null) {
                taskInDb = getTask(id);
            }
            if (title != null) {
                taskInDb = taskRepository.findByTitle(title);
            }
            taskInDb.setList(null);
            taskInDb.setUsers(null);
            return new ResponseEntity<>(taskInDb, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> modifyTask(Task task) {
        try {
            updateTask(task.getId(), task);
            return new ResponseEntity<>("Modify task successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> createTask(Task task) {
        try {
            saveTask(task);
            return new ResponseEntity<>("Create task successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> assignMembersToTask(@RequestBody Task task) {
        try {
            Task taskInDb = getTask(task.getId());
            if(taskInDb==null){
                return new ResponseEntity<>("Can't find task object!", HttpStatus.BAD_REQUEST);
            }
            for(User user:task.getUsers()){
                User userInDb = userservice.findByEmail(user.getEmail());
                userInDb.getTasks().add(taskInDb);
                userservice.saveUser(userInDb);
            }
            //workspaceService.saveWorkspace(workspaceInDb);
            return new ResponseEntity<>("Assign members to task successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

        if(Objects.nonNull(newTask.getStartDate()) &&
                !"".equalsIgnoreCase(newTask.getStartDate())) {
            oldTask.setStartDate(newTask.getStartDate());
        }

        if(Objects.nonNull(newTask.getDeadline()) &&
                !"".equalsIgnoreCase(newTask.getDeadline())) {
            oldTask.setDeadline(newTask.getDeadline());
        }

        if(Objects.nonNull(newTask.getCompletionDate()) &&
                !"".equalsIgnoreCase(newTask.getCompletionDate())) {
            oldTask.setCompletionDate(newTask.getCompletionDate());
        }

        oldTask.setActiveFlag(newTask.getActiveFlag());

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
