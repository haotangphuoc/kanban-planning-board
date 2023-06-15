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

    //Constructor
    public Board(int id, String title) {
        this.id = id;
        this.title = title;
        //Initialization of list can be added here
    }

    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public List<Group18.Demo.Trello.model.List> getLists() {
        return lists;
    }

    public void setLists(List<Group18.Demo.Trello.model.List> lists) {
        this.lists = lists;
    }
}
