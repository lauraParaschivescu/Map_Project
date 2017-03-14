package Repository;

import Domain.Position;
import Domain.Task;
import Validator.IValidator;

public class TaskRepository extends AbstractRepository<Task, Integer> {
    public TaskRepository(IValidator<Task> val)
    {
        super(val);
    }
}
