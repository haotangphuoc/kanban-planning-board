package Group18.Demo.Trello.repository;

import Group18.Demo.Trello.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    public Board findByTitle(String name);
}
