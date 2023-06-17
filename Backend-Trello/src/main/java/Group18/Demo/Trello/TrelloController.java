package Group18.Demo.Trello;

import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.UserRepository;
import Group18.Demo.Trello.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TrelloController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            if(userRepository.existsByEmail(user.getEmail())){
                return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
            }

            User _tutorial = userRepository
                    .save(new User(-1, user.getEmail(), user.getPassword(), user.getQuestionAns(), user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            User userRes = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
            if(userRes==null){
                return new ResponseEntity<>("Invalid email or password!", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("User login successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createWorkspace")
    public ResponseEntity<String> createWorkspace(@RequestBody User user) {

        try {
            User userInDb = userRepository.findById(user.getId()).get();
            if(userInDb==null){
                return new ResponseEntity<>("Can't find user object!", HttpStatus.BAD_REQUEST);
            }
            userInDb.setWorkspaces(user.getWorkspaces());
            for(Workspace workspace:user.getWorkspaces()){
                workspaceRepository.save(workspace);
            }
            userRepository.save(userInDb);
            return new ResponseEntity<>("Create workspace successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
