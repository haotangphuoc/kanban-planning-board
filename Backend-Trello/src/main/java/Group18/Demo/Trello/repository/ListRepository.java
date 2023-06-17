package Group18.Demo.Trello.repository;

import Group18.Demo.Trello.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Integer> {
}
