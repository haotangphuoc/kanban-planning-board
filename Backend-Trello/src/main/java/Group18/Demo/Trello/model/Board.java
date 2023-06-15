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
        name = "board"
)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "board_id",
            nullable = false
    )
    private int id;
    @Column(
            name = "board_title",
            nullable = false
    )
    private String title;

    //Dependency List
    @ManyToMany(mappedBy = "boards")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name="workspace_id", nullable = false, referencedColumnName = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "board")
    private List<Group18.Demo.Trello.model.List> lists;
}
