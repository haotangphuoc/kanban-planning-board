package Group18.Demo.Trello;

import Group18.Demo.Trello.model.*;
import Group18.Demo.Trello.repository.UserRepository;
import Group18.Demo.Trello.repository.WorkspaceRepository;
import Group18.Demo.Trello.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            if(userService.existsByEmail(user.getEmail())){
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }

            userService.saveUser(new User(-1, user.getEmail(), user.getPassword(), user.getQuestionAns(), user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            User userRes = userService.findByEmailAndPassword(user.getEmail(),user.getPassword());
            if(userRes==null){
                return new ResponseEntity<>("Invalid email or password!", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("User login successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createWorkspace")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createWorkspace(@RequestBody User user) {

        try {
            User userInDb = userService.getUser(user.getId());
            if(userInDb==null){
                return new ResponseEntity<>("Can't find user object!", HttpStatus.BAD_REQUEST);
            }
            userInDb.setWorkspaces(user.getWorkspaces());
            for(Workspace workspace:user.getWorkspaces()){
                workspaceService.saveWorkspace(workspace);
            }
            userService.saveUser(userInDb);
            return new ResponseEntity<>("Create workspace successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createBoard")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createBoard(@RequestBody User user) {

        try {
            User userInDb = userService.getUser(user.getId());
            if(userInDb==null){
                return new ResponseEntity<>("Can't find user object!", HttpStatus.BAD_REQUEST);
            }
            userInDb.setBoards(user.getBoards());
            for(Board board:user.getBoards()){
                Workspace workspaceInDb = workspaceService.getWorkspace(board.getWorkspace().getId());
                board.setWorkspace(workspaceInDb);
                boardService.saveBoard(board);
            }
            return new ResponseEntity<>("Create board successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleteBoard")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteBoard(@RequestParam("boardId") Integer boardId) {

        try {
            String res = boardService.deleteBoard(boardId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/resetPassword")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        try {
            User userInDb = userService.findByEmail(user.getEmail());
            if(userInDb==null){
                return new ResponseEntity<>("Can't find user object!", HttpStatus.BAD_REQUEST);
            }
            if(!user.getQuestionAns().equals(userInDb.getQuestionAns())){
                return new ResponseEntity<>("Your answer is not correct!", HttpStatus.BAD_REQUEST);
            }
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>("Reset password successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addMembersToWorkspace")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> addMembersToWorkspace(@RequestBody Workspace workspace) {

        try {
            Workspace workspaceInDb = workspaceService.getWorkspace(workspace.getId());
            if(workspaceInDb==null){
                return new ResponseEntity<>("Can't find workspace object!", HttpStatus.BAD_REQUEST);
            }
            for(User user:workspace.getUsers()){
                User userInDb = userService.findByEmail(user.getEmail());
                userInDb.getWorkspaces().add(workspaceInDb);
                userService.saveUser(userInDb);
            }
            //workspaceService.saveWorkspace(workspaceInDb);
            return new ResponseEntity<>("Add members to workspace successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/findUserIdByEmail")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> findUserIdByEmail(@RequestParam("email") String email) {
        try {
            User user = userService.findByEmail(email);
            if (user == null) {
                return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findWorkspaceIdByName")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> findWorkspaceIdByName(@RequestParam("name") String name) {
        try {
            Workspace workspace = workspaceService.findByName(name);
            if (workspace == null) {
                return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(workspace.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
