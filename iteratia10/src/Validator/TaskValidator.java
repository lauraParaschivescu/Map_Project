package Validator;
import Domain.Position;
import Domain.Task;
import Exception.CustomException;

public class TaskValidator implements IValidator<Task> {
    public void validate(Task t) {
        StringBuilder err = new StringBuilder();

        if(t.getId() < 0)
            err.append("\nid-ul jobului nu poate fi negativ\n");
        if(t.getDescription().isEmpty())
            err.append("n-ati introdus nici o descriere a sarcinii\n");
        if(t.getDuration() <= 0)
            err.append("n-ati introdus o durata pozitiva a sarcinii\n");

        if(err.length() > 0)
            throw new CustomException(err.toString());
    }
}
