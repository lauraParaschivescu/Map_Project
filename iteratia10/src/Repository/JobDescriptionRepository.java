package Repository;

import Domain.JobDescription;
import java.util.ArrayList;
import java.util.List;

public class JobDescriptionRepository {
    protected List<JobDescription> entities;

    // Constructor
    public JobDescriptionRepository()
    {
        entities = new ArrayList<>();
    }

    // Adaugarea unei entitati
    public void add(JobDescription entity)
    {
        entities.add(entity);
    }

    // Intoarcerea vectorului cu entitati
    public List<JobDescription> getAll() {
        return entities;
    }

    // Stergere entitate
    public void delete(JobDescription entity)
    {
        entities.remove(entity);

    }
}
