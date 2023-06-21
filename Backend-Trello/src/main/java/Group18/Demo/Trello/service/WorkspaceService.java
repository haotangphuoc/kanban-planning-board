package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;

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

    public String updateWorkspace(int workspaceId, Workspace newWorkspace) {
        Workspace oldWorkspace = workspaceRepository.findById(workspaceId).get();

        if(Objects.nonNull(newWorkspace.getName()) &&
                !"".equalsIgnoreCase(newWorkspace.getName())) {
            oldWorkspace.setName(newWorkspace.getName());
        }

        if(Objects.nonNull(newWorkspace.getDescription()) &&
                !"".equalsIgnoreCase(newWorkspace.getDescription())) {
            oldWorkspace.setDescription(newWorkspace.getDescription());
        }

        workspaceRepository.save(oldWorkspace);
        return "Workspace successfully updated";
    }

    public List<User> fetchUserById(int workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        return workspace.getUsers();
    }

    public List<Workspace> fetchWorkspaceList() {
        return workspaceRepository.findAll();
    }

    public List<Board> fetchBoardById(int workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        return workspace.getBoards();
    }
}
