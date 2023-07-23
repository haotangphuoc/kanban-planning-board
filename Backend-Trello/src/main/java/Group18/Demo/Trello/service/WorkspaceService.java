package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    UserService userService;

    public ResponseEntity<String> createWorkspace(User user) {
        try {
            User userInDb = userService.getUser(user.getId());
            if(userInDb==null){
                return new ResponseEntity<>("Can't find user object!", HttpStatus.BAD_REQUEST);
            }
            List<Workspace> workspacesInDb = userInDb.getWorkspaces();
            for(Workspace workspace:user.getWorkspaces()){
                saveWorkspace(workspace);
                workspacesInDb.add(workspace);
            }
            userInDb.setWorkspaces(workspacesInDb);
            userService.saveUser(userInDb);
            return new ResponseEntity<>("Create workspace successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addMembersToWorkspace(Workspace workspace) {

        try {
            Workspace workspaceInDb = getWorkspace(workspace.getId());
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

    public ResponseEntity<Integer> findWorkspaceIdByName(String name) {
        try {
            Workspace workspace = findByName(name);
            if (workspace == null) {
                return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(workspace.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> modifyWorkspace(Workspace workspace) {
        try {
            Workspace workspaceInDb = getWorkspace(workspace.getId());
            if (workspaceInDb == null) {
                return new ResponseEntity<>("Can't find workspace object!", HttpStatus.BAD_REQUEST);
            }
            workspaceInDb.setName(workspace.getName());
            saveWorkspace(workspaceInDb);
            workspaceInDb.setDescription(workspace.getDescription());
            saveWorkspace(workspaceInDb);
            return new ResponseEntity<>("Modify workspace successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Board>> fetchBoardById(Integer workspaceId) {
        try {
            Workspace workspace = getWorkspace(workspaceId);
            List<Board> boards = workspace.getBoards();
            if(boards != null) {
                return new ResponseEntity<>(boards, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Workspace getWorkspace(int workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        return workspace;
    }

    public String saveWorkspace(Workspace workspace) {
        workspaceRepository.save(workspace);
        return "Workspace successfully saved";
    }

    public String deleteWorkspace(int workspaceId) {
        workspaceRepository.deleteById(workspaceId);
        return "Workspace successfully deleted";
    }

    public List<User> fetchUserById(int workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        return workspace.getUsers();
    }

    public List<Workspace> fetchWorkspaceList() {
        return workspaceRepository.findAll();
    }

    public  Workspace findByName(String name) {
        return workspaceRepository.findByName(name);
    }
}
