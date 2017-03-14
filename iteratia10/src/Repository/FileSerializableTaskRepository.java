package Repository;
import Domain.Task;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidatorException;
import java.util.*;
import java.io.*;

public class FileSerializableTaskRepository extends AbstractRepository<Task, Integer> {
    private String fileName;

    public FileSerializableTaskRepository(IValidator<Task> val, String fileName)
    {
        super(val);
        this.fileName = fileName;
        loadFromFile();
    }

    private void loadFromFile() {
        try
        {
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)))
            {
                entities = (List<Task>)in.readObject();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            throw new RepositoryException(e);
        }
    }

    private void saveToFile() {
        // Varianta seminar
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(entities);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            throw new RepositoryException(e);
        }
    }

    public void add(Task t) {
        super.add(t);
        saveToFile();
    }

    public void delete(Integer id) {
        super.delete(id);
        saveToFile();
    }

    public void update(Task task)
    {
        super.update(task);
        saveToFile();
    }
}
