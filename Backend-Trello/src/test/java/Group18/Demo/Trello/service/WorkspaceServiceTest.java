package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.WorkspaceRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceServiceTest {
    @InjectMocks
    private WorkspaceService workspaceService;
    @Mock
    private WorkspaceRepository workspaceRepository;
    @Mock
    private UserService userService;

    @Mock
    private BoardService boardService;

    public List<Workspace> createSampleWorkspaces() {
        List<Workspace> workspaces = new ArrayList<>();
        Workspace workspace1 = new Workspace(1, "workspace 1", "description");
        Workspace workspace2 = new Workspace(2, "workspace 2", "description");

        workspaces.add(workspace1);
        workspaces.add(workspace2);
        return workspaces;
    }

    @Test
    void createWorkspace_InvalidUser() {
        User user = new User();
        user.setId(1);

        when(userService.getUser(user.getId())).thenReturn(null);

        ResponseEntity responseEntity = workspaceService.createWorkspace(user);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Workspace should not be created");
        assertEquals("Can't find user object!", responseEntity.getBody(), "Wrong message");

        verify(userService).getUser(user.getId());
    }

    @Test
    void createWorkspace_ValidUser() {
        User user = new User();
        user.setId(1);
        user.setWorkspaces(createSampleWorkspaces());

        User userInDb = new User();
        userInDb.setId(user.getId());

        when(userService.getUser(user.getId())).thenReturn(userInDb);

        ResponseEntity responseEntity = workspaceService.createWorkspace(user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Create workspace successfully!", responseEntity.getBody());

        verify(userService).getUser(user.getId());
    }

    @Test
    void addMembersToWorkspace() {
        Workspace workspace = new Workspace(1, "sample", "description");

        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        User user2 = new User();
        user2.setEmail("user2@gmail.com");

        when(workspaceRepository.findById(1)).thenReturn(Optional.of(workspace));

        ResponseEntity response = workspaceService.addMembersToWorkspace(workspace);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Add members to workspace successfully!", response.getBody());
    }

    @Test
    void findWorkspaceIdByName_ValidWorkspace() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");
        when(workspaceService.findByName(workspace.getName())).thenReturn(workspace);

        ResponseEntity response = workspaceService.findWorkspaceIdByName("sample workspace");

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Workspace Id should be returned");
        assertEquals(workspace.getId(), response.getBody(), "Wrong value");

        verify(workspaceRepository).findByName(workspace.getName());
    }

    @Test
    void findWorkspaceIdByName_InvalidWorkspace() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");

        when(workspaceService.findByName(workspace.getName())).thenReturn(null);

        ResponseEntity response = workspaceService.findWorkspaceIdByName(workspace.getName());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Workspace Id should not be returned");
        assertEquals(-1, response.getBody(), "Wrong value");

        verify(workspaceRepository).findByName(workspace.getName());
    }

    @Test
    void fetBoardById_Success() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");
        List<Board> boards = new ArrayList<>();
        boards.add(new Board());
        workspace.setBoards(boards);

        when(workspaceRepository.findById(1)).thenReturn(Optional.of(workspace));

        ResponseEntity<List<Board>> responseEntity = workspaceService.fetchBoardById(1);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(boards, responseEntity.getBody());
    }

    @Test
    void fetchBoardById_NotFound() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");

        when(workspaceRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<Board>> responseEntity = workspaceService.fetchBoardById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void getWorkspace() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");

        when(workspaceRepository.findById(workspace.getId())).thenReturn(Optional.of(workspace));
        Workspace result = workspaceService.getWorkspace(workspace.getId());
        assertNotNull(result);
        assertEquals(workspace, result);
    }

    @Test
    void saveWorkspace() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");

        when(workspaceRepository.save(workspace)).thenReturn(workspace);

        String result = workspaceService.saveWorkspace(workspace);
        assertEquals("Workspace successfully saved", result);

        verify(workspaceRepository).save(workspace);
    }

    @Test
    void findByName() {
        Workspace workspace = new Workspace(1, "sample workspace", "description");

        when(workspaceRepository.findByName(workspace.getName())).thenReturn(workspace);
        Workspace result = workspaceService.findByName(workspace.getName());
        assertEquals(workspace, result);
    }
}