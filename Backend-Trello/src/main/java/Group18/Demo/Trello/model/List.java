package Group18.Demo.Trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Board board;

    @OneToMany(mappedBy = "list")
    private java.util.List<Task> tasks;
}