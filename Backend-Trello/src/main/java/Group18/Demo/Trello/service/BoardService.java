package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.model.Workspace;
import Group18.Demo.Trello.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserService userService;
    @Autowired
    WorkspaceService workspaceService;

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
                saveBoard(board);
            }
            return new ResponseEntity<>("Create board successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteBoard(@RequestParam("boardId") Integer boardId) {
        try {
            boardRepository.deleteById(boardId);
            return new ResponseEntity<>("Board successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Board getBoard(int boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board;
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
        boardRepository.save(board);
        return "Board successfully saved";
    }

    public String updateList(int boardId, Board newBoard) {
        Board oldBoard = boardRepository.findById(boardId).get();

        if(Objects.nonNull(newBoard.getTitle()) &&
                !"".equalsIgnoreCase(newBoard.getTitle())) {
            oldBoard.setTitle(newBoard.getTitle());
        }

        boardRepository.save(oldBoard);
        return "Board successfully updated";
    }

    public List<User> fetchUserById(int boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board.getUsers();
    }

    public List<Board> fetchBoardList() {
        return boardRepository.findAll();
    }

    public List<Group18.Demo.Trello.model.List> fetchListById(int boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board.getLists();
    }

    public Board findByTitle(String name) {
        return boardRepository.findByTitle(name);
    }
}
