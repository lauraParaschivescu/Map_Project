package Domain;

import java.util.List;

public class JobDescription {
    private Position position;
    private List<Task> tasks;

    public JobDescription(Position position, List<Task> tasks) {
        this.position = position;
        this.tasks = tasks;
    }

    public Position getPosition() {
        return position;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
