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
}
