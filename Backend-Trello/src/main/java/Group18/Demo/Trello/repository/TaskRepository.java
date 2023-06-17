package Group18.Demo.Trello.repository;

import Group18.Demo.Trello.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
