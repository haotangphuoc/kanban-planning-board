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
        name = "user"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JsonIgnoreProperties("users")
    private List<Workspace> workspaces;

    @ManyToMany //Cascade can be added here
    @JoinTable(name="user_board",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name="board_id", referencedColumnName = "board_id"))
    @JsonIgnoreProperties("users")
    private List<Board> boards;

    @ManyToMany //Cascade can be added here
    @JoinTable(name="user_task",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName = "task_id"))
    @JsonIgnoreProperties("users")
    private List<Task> tasks;

    //Constructor

    public User(String email, String password, String questionAns, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.questionAns = questionAns;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestionAns() {
        return questionAns;
    }

    public void setQuestionAns(String questionAns) {
        this.questionAns = questionAns;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
