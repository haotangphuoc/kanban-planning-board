package Group18.Demo.Trello.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "list"
)
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "list_id",
            nullable = false
    )
    private int id;
    @Column(
            name = "status",
            nullable = false
    )
    private String status;

    //Dependency Task
    @ManyToOne
    @JoinColumn(name="board_id", nullable = false, referencedColumnName = "board_id")
    @JsonIgnoreProperties("lists")
    private Board board;

    @OneToMany(mappedBy = "list")
    @JsonIgnoreProperties("list")
    private java.util.List<Task> tasks = new ArrayList<>();

    //Constructor
    public List(Board board, String status) {
        this.board = board;
        this.status = status;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public java.util.List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(java.util.List<Task> tasks) {
        this.tasks = tasks;
    }
}