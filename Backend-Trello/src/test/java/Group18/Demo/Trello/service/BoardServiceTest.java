package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.BoardRepository;
import Group18.Demo.Trello.repository.UserRepository;
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
class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserService userService;

    @Mock
    private WorkspaceService workspaceService;

    //Helper methods that create board for test cases
    private List<Board> createSampleBoards() {
        List<Board> boards = new ArrayList<>();

        Workspace workspace = new Workspace(1, "sample workspace", "description");
        Board board1 = new Board(1, "board 1");
        Board board2 = new Board(2, "board 2");

        board1.setWorkspace(workspace);
        board2.setWorkspace(workspace);

        boards.add(board1);
        boards.add(board2);
        return boards;
    }


    @Test
    void createBoard_InvalidUser() {
        //To be implemented
    }


    @Test
    void createBoard_ValidUser() {
        //To be implemented
    }


    @Test
    void deleteBoard_ValidWorkspace() {
        Integer boardId = 1;

        ResponseEntity responseEntity = boardService.deleteBoard(boardId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Board should be deleted for valid board id");
        assertEquals("Board successfully deleted", responseEntity.getBody(), "Wrong message");
    }

    @Test
    void findBoardIdByTitle_ValidBoard() {
        Board board = new Board(1, "sample board");
        when(boardService.findByTitle(board.getTitle())).thenReturn(board);

        ResponseEntity responseEntity = boardService.findBoardIdByTitle(board.getTitle());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Board Id should be found");
        assertEquals(board.getId(), responseEntity.getBody(), "Wrong value");
    }

    @Test
    void findBoardIdByTitle_InvalidBoard() {
        Board board = new Board(1, "sample board");
        when(boardService.findByTitle(board.getTitle())).thenReturn(null);

        ResponseEntity responseEntity = boardService.findBoardIdByTitle(board.getTitle());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Board Id should not be found");
        assertEquals(-1, responseEntity.getBody(), "Wrong value");
    }
    @Test
    public void fetBoardById_Success() {
        Board board = new Board(1, "sample board");
        List<Group18.Demo.Trello.model.List> lists = new ArrayList<>();
        lists.add(new Group18.Demo.Trello.model.List());
        board.setLists(lists);

        when(boardRepository.findById(1)).thenReturn(Optional.of(board));

        ResponseEntity<List<Group18.Demo.Trello.model.List>> responseEntity = boardService.fetchListById(1);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(lists, responseEntity.getBody());
    }
}