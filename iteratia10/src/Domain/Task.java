package Domain;
import java.io.Serializable;

public class Task implements IBaseEntity<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String description;
    private Integer duration;

    public Task(Integer id, String description, Integer duration)
    {
        this.id = id;
        this.description = description;
        this.duration = duration;
    }

    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public String getDescription() {
        return this.description;
    }
    public Integer getDuration() { return this.duration; }

    // Suprascrierea metodei toString necesara pt afisarea unei entitati
    public String toString() {
        return "Task: " + getId() + " " + getDescription() + " " + getDuration();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
