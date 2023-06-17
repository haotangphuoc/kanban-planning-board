package Group18.Demo.Trello.repository;

import Group18.Demo.Trello.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmailAndPassword(String email, String password);

    Boolean existsByEmail(String email);
}
