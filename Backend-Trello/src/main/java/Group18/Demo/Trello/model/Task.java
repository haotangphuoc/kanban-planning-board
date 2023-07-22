package Group18.Demo.Trello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String activeFlag;

    @Column(
            name = "start_date",
            nullable = false
    )
    private String startDate;

    @Column(
            name = "deadline",
            nullable = false
    )
    private String deadline;

    @Column(
            name = "completion_date",
            nullable = false
    )
    private String completionDate;

    //Dependencies
    @ManyToMany(mappedBy = "tasks")
    @JsonIgnoreProperties("tasks")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="list_id", nullable = false, referencedColumnName = "list_id")
    @JsonIgnoreProperties("tasks")
    private Group18.Demo.Trello.model.List list;

    //Constructors
    public Task(int id, String title, String description, String isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.activeFlag = isActive;
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

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }
}
