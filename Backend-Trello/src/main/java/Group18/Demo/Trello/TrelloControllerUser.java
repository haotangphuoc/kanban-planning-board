package Group18.Demo.Trello;

import Group18.Demo.Trello.model.*;
import Group18.Demo.Trello.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TrelloControllerUser {
    @Autowired
    UserService userService;
    
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

}
