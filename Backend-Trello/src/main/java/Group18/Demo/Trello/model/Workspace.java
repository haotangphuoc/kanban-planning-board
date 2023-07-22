package Group18.Demo.Trello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JsonIgnoreProperties("workspaces")
    private List<User> users;

    @OneToMany(mappedBy = "workspace")
    @JsonIgnoreProperties("workspace")
    private List<Board> boards;

    //Constructor
    public Workspace(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        //Initialization of list can be added here
    }

    public Workspace(String name, String description, List<User> users, List<Board> boards) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.boards = boards;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }
}
