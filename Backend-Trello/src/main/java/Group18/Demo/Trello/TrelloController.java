package Group18.Demo.Trello;

import Group18.Demo.Trello.model.*;
import Group18.Demo.Trello.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/home")
    public String home() {
        return "This is home endpoint";
    }

    @PostMapping("/signup")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> login(@RequestBody User user) {
        return userService.login(user);
    }


    @PostMapping("/createWorkspace")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createWorkspace(@RequestBody User user) {
        return workspaceService.createWorkspace(user);
    }

    @PostMapping("/createBoard")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createBoard(@RequestBody User user) {
        return boardService.createBoard(user);
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
}
