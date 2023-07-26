package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;

import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.BoardRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    private WorkspaceService workspaceService;

    @Mock
    private ListService listService;

    @Test
    void createBoard_ValidWorkspace() {
        Workspace workspace = new Workspace(1, "Sample workspace", "Workspace description");
        Board board = new Board(1, "Sample board");
        List<Board> workspaceBoards = new ArrayList<>();
        workspaceBoards.add(board);
        workspace.setBoards(workspaceBoards);

        when(workspaceService.getWorkspace(1)).thenReturn(workspace);

        ResponseEntity<String> response = boardService.createBoard(workspace);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Create board successfully!", response.getBody());
    }
    @Test
    void createBoard_InvalidWorkspace() {
        Workspace workspace = new Workspace(1, "Sample workspace", "Workspace description");

        when(workspaceService.getWorkspace(1)).thenReturn(null);

        ResponseEntity<String> response = boardService.createBoard(workspace);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Can't find workspace object!", response.getBody());
    }

    @Test
    void createBoard_InternalServerError() {
        Workspace workspace = new Workspace(1, "Sample workspace", "Workspace description");
        Board board = new Board(1, "Sample board");
        List<Board> workspaceBoards = new ArrayList<>();
        workspaceBoards.add(board);
        workspace.setBoards(workspaceBoards);

        when(workspaceService.getWorkspace(1)).thenReturn(workspace);
        doThrow(new RuntimeException("Internal Server Error")).when(listService).createListsForBoard(any(Board.class));

        ResponseEntity<String> response = boardService.createBoard(workspace);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }


    @Test
    void deleteBoard_ValidWorkspace() {
        Integer boardId = 1;

        doNothing().when(boardRepository).deleteById(boardId);

        ResponseEntity responseEntity = boardService.deleteBoard(boardId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Board should be deleted for valid board id");
        assertEquals("Board successfully deleted", responseEntity.getBody(), "Wrong message");
    }

    @Test
    void deleteBoard_InternalServerError() {
        Integer boardId = 1;

        doThrow(new RuntimeException("Internal Server Error")).when(boardRepository).deleteById(boardId);

        ResponseEntity responseEntity = boardService.deleteBoard(boardId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode(), "Board should be deleted for valid board id");
        assertEquals("Error", responseEntity.getBody(), "Wrong message");
    }

    @Test
    void getBoard_ValidBoardId() {
        Integer boardId = 1;
        Board board = new Board(boardId, "Sample board");

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        Board returnBoard = boardService.getBoard(1);
        assertEquals(board, returnBoard);
        verify(boardRepository).findById(boardId);
    }

    @Test
    void getBoard_InternalServerError() {
        Integer boardId = 69;

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> boardService.getBoard(boardId));
        assertEquals("Board not found with ID: " + boardId, exception.getMessage());
        verify(boardRepository).findById(boardId);
    }

    @Test
    void findBoardIdByTitle_ValidBoardId() {
        Board board = new Board(1, "sample board");
        when(boardService.findByTitle(board.getTitle())).thenReturn(board);

        ResponseEntity responseEntity = boardService.findBoardIdByTitle(board.getTitle());
        assertEquals(HttpStatus.OK, boardService.findBoardIdByTitle(board.getTitle()).getStatusCode(), "Board Id should be found");
        assertEquals(board.getId(), responseEntity.getBody(), "Wrong value");
    }

    @Test
    void findBoardIdByTitle_InvalidBoard() {
        Board board = new Board(1, "sample board");
        when(boardService.findByTitle(board.getTitle())).thenReturn(null);

        ResponseEntity responseEntity = boardService.findBoardIdByTitle(board.getTitle());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Board Id should not be found");
        assertEquals(-1, responseEntity.getBody(), "Wrong value");
    }

    @Test
    void findBoardIdByTitle_InternalServerError() {
        String boardTittle = "Not exist";
        when(boardService.findByTitle(boardTittle)).thenThrow(new RuntimeException());

        ResponseEntity responseEntity = boardService.findBoardIdByTitle(boardTittle);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(-1, responseEntity.getBody(), "Wrong value");
    }

    @Test
    void saveBoard_ValidBoard() {
        Board board = new Board(1, "Sample board");

        String result = boardService.saveBoard(board);
        assertEquals("Board successfully saved", result);
        verify(boardRepository).save(board);
    }

    @Test
    void saveBoard_InternalServerError() {
        Board board = new Board(1, "Sample board");

        doThrow(new RuntimeException("Some database error")).when(boardRepository).save(board);

        String result = boardService.saveBoard(board);
        assertEquals("Error: Unable to save board", result);
        verify(boardRepository).save(board);
    }

    @Test
    void fetchListById_ValidBoardId() {
        Board board = new Board(1, "Sample board");
        List<Group18.Demo.Trello.model.List> lists = new ArrayList<>();
        lists.add(new Group18.Demo.Trello.model.List(board, "to-do"));
        lists.add(new Group18.Demo.Trello.model.List(board, "doing"));
        lists.add(new Group18.Demo.Trello.model.List(board, "done"));
        board.setLists(lists);

        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

        ResponseEntity<List<Group18.Demo.Trello.model.List>> response = boardService.fetchListById(board.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lists, response.getBody());
    }
    @Test
    void fetchListById_InternalServerError() {
        Integer boardId = 69;

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        ResponseEntity<List<Group18.Demo.Trello.model.List>> response = boardService.fetchListById(boardId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void findByTitle_ExistingBoard() {
        Board board = new Board(1, "Sample Board");

        when(boardRepository.findByTitle(board.getTitle())).thenReturn(board);

        Board returnBoard = boardService.findByTitle(board.getTitle());
        assertEquals(board, returnBoard);
    }

    @Test
    void findByTitle_NotFoundBoard() {
        String boardTitle = "Not exist";

        when(boardRepository.findByTitle(boardTitle)).thenReturn(null);

        Board returnBoard = boardService.findByTitle(boardTitle);
        assertEquals(null, returnBoard);
    }
}