package Service;

import Domain.Task;
import Repository.IRepository;
import Utils.IObservable;
import Utils.IObserver;
import Validator.RepositoryException;
import Validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public class TaskService implements IObservable<Task> {
    private IRepository<Task, Integer> repo;
    private List<IObserver<Task>> observers = new ArrayList<>();

    public TaskService(IRepository<Task, Integer> repo) {
        this.repo = repo;
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        Iterable<Task> listTasks = repo.getAll();

        for(Task t : listTasks) {
            tasks.add(t);
        }

        return tasks;
    }

    public void add(Task t) {
        try {
            repo.add(t);
            notifyObservers();
        }catch(ValidatorException e) {
            throw new RepositoryException(e);
        }
    }

    public void delete(Integer id) {
        try {
            repo.delete(id);
            notifyObservers();
        }catch(ValidatorException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(Task t) {
        try {
            repo.update(t);
            notifyObservers();
        }catch(ValidatorException e) {
            throw new RepositoryException(e);
        }
    }

    public void addObserver(IObserver<Task> o) {
        observers.add(o);
    }

    public void deleteObserver(IObserver<Task> o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for(IObserver<Task> o : observers) {
            o.update(getAll());
        }
    }
}
