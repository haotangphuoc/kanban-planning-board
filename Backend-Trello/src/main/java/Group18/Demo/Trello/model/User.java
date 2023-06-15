package Group18.Demo.Trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "user_id",
            nullable = false
    )
    private int id;
    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    @Column(
            name = "questionans",
            nullable = false
    )
    private String questionAns;
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    //Dependencies
    @ManyToMany //Cascade can be added here
    @JoinTable(name="user_workspace",
    joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name="workspace_id", referencedColumnName = "workspace_id"))
    private List<Workspace> workspaces;

    @ManyToMany //Cascade can be added here
    @JoinTable(name="user_board",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name="board_id", referencedColumnName = "board_id"))
    private List<Board> boards;

    @ManyToMany //Cascade can be added here
    @JoinTable(name="user_task",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName = "task_id"))
    private List<Task> tasks;
}
