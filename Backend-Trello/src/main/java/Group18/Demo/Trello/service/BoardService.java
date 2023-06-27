package Group18.Demo.Trello.service;

import Group18.Demo.Trello.model.Board;
import Group18.Demo.Trello.model.User;
import Group18.Demo.Trello.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public Board getBoard(int boardId) {
        Board board = boardRepository.findById(boardId).get();
        return board;
    }

    public String saveBoard(Board board) {
        boardRepository.save(board);
        return "Board successfully saved";
    }

    public String deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
        return "Board successfully deleted";
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
