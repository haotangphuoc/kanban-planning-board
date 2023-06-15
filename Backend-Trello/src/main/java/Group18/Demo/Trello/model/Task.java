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
        name = "task"
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "task_id",
            nullable = false
    )
    private int id;
    @Column(
            name = "task_title",
            nullable = false
    )
    private String title;
    @Column(
            name = "task_description"
    )
    private String description;

    //Date

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean isActive;

    //Dependencies
    @ManyToMany(mappedBy = "tasks")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name="list_id", nullable = false, referencedColumnName = "list_id")
    private Group18.Demo.Trello.model.List list;

    //Constructors
    public Task(int id, String title, String description, boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Group18.Demo.Trello.model.List getList() {
        return list;
    }

    public void setList(Group18.Demo.Trello.model.List list) {
        this.list = list;
    }
}
