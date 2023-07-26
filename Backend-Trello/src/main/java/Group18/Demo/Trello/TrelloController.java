package Group18.Demo.Trello;

import Group18.Demo.Trello.model.*;
import Group18.Demo.Trello.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrelloController {
    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    UserService userService;

    @Autowired
    BoardService boardService;

    @Autowired
    TaskService taskService;

    @Autowired
    ListService listService;

    @PostMapping("/createWorkspace")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createWorkspace(@RequestBody User user) {
        return workspaceService.createWorkspace(user);
    }

    @PostMapping("/createBoard")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createBoard(@RequestBody Workspace workspace) {
        return boardService.createBoard(workspace);
    }

    @GetMapping("/deleteBoard")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteBoard(@RequestParam("boardId") Integer boardId) {
        return boardService.deleteBoard(boardId);
    }

    @PostMapping("/resetPassword")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        return userService.resetPassword(user);
    }

    @PostMapping("/addMembersToWorkspace")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> addMembersToWorkspace(@RequestBody Workspace workspace) {
        return workspaceService.addMembersToWorkspace(workspace);
    }

    @GetMapping("/findUserIdByEmail")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> findUserIdByEmail(@RequestParam("email") String email) {
        return userService.findUserIdByEmail(email);
    }

    @GetMapping("/findWorkspaceIdByName")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> findWorkspaceIdByName(@RequestParam("name") String name) {
        return workspaceService.findWorkspaceIdByName(name);
    }

    @GetMapping("/findBoardIdByTitle")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> findBoardIdByTitle(@RequestParam("title") String title) {
        return boardService.findBoardIdByTitle(title);
    }

    @PostMapping("/modifyWorkspace")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> modifyWorkspace(@RequestBody Workspace workspace) {
        return workspaceService.modifyWorkspace(workspace);
    }

    @GetMapping("/findTaskByIdOrTitle")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Task> findTaskByIdOrTitle(@RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "title", required = false) String title) {
        return taskService.findTaskByIdOrTitle(id, title);
    }

    @PostMapping("/createTask")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> createTask(@RequestBody Group18.Demo.Trello.model.List list) {
        return taskService.createTask(list);
    }

    @PostMapping("/assignMembersToTask")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> assignMembersToTask(@RequestBody Task task) {
        return taskService.assignMembersToTask(task);
    }

    @GetMapping("/getUserWorkspaces")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Workspace>> getUserWorkspaces(@RequestParam(value = "id") Integer userId) {
        return userService.fetchWorkspaceById(userId);
    }

    @GetMapping("/getUserBoards")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Board>> getUserBoards(@RequestParam(value = "id") Integer userId) {
        return userService.fetchBoardById(userId);
    }

    @GetMapping("/getUserTasks")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Task>> getUserTasks(@RequestParam(value = "id") Integer userId) {
        return userService.fetchTaskById(userId);
    }

    @GetMapping("/getWorkspaceBoards")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Board>> getWorkspaceBoards(@RequestParam(value = "id") Integer workspaceId) {
        return workspaceService.fetchBoardById(workspaceId);
    }

    @GetMapping("/getBoardLists")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Group18.Demo.Trello.model.List>> getBoardLists(
            @RequestParam(value = "id") Integer boardId) {
        return boardService.fetchListById(boardId);
    }

}
