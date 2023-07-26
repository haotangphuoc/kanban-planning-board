package Group18.Demo.Trello.repository;

import Group18.Demo.Trello.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace,Integer> {
    public Workspace findByName(String name);
}
