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
        name = "workspace"
)
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "workspace_id",
            nullable = false
    )
    private int id;
    @Column(
            name = "workspace_name",
            nullable = false
    )
    private String name;
    @Column(
            name = "workspace_description"
    )
    private String description;

    //Dependencies
    @ManyToMany(mappedBy = "workspaces")
    private List<User> users;

    @OneToMany(mappedBy = "workspace")
    private List<Board> boards;
}
