package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserService userService;
    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    ListService listService;

    public ResponseEntity<String> createBoard(Workspace workspace) {
        try {
            Workspace workspaceInDb = workspaceService.getWorkspace(workspace.getId());
            if(workspaceInDb==null){
                return new ResponseEntity<>("Can't find workspace object!", HttpStatus.BAD_REQUEST);
            }
            List<Board> boardInDb = new ArrayList<>(workspaceInDb.getBoards());
            //Create new board instances for the submitted data
            for(Board board:workspace.getBoards()){
                Board newBoard = new Board();
                newBoard.setWorkspace(workspaceInDb);
                newBoard.setTitle(board.getTitle());
                saveBoard(newBoard);

                //Create to do, doing and done lists for board
                listService.createListsForBoard(newBoard);
                boardInDb.add(newBoard);
            }

            //Save boards to workspace database
            workspaceInDb.setBoards(boardInDb);
            workspaceService.saveWorkspace(workspaceInDb);

            return new ResponseEntity<>("Create board successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteBoard(Integer boardId) {
        try {
            boardRepository.deleteById(boardId);
            return new ResponseEntity<>("Board successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Board getBoard(int boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found with ID: " + boardId));
    }

    public ResponseEntity<Integer> findBoardIdByTitle(String title) {
        try {
            Board board = findByTitle(title);
            if (board == null) {
                return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(board.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String saveBoard(Board board) {
        try {
            boardRepository.save(board);
            return "Board successfully saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to save board";
        }
    }

    public ResponseEntity<List<Group18.Demo.Trello.model.List>> fetchListById(Integer boardId) {
        try {
            Board board = getBoard(boardId);
            List<Group18.Demo.Trello.model.List> lists = board.getLists();
            if(lists != null) {
                return new ResponseEntity<>(lists, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Board findByTitle(String name) {
        return boardRepository.findByTitle(name);
    }
}
