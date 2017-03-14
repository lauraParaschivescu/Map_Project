package Service;

import Domain.Position;
import Repository.IRepository;
import Validator.RepositoryException;
import Validator.ValidatorException;
import Utils.*;
import java.util.ArrayList;
import java.util.List;

public class PositionService implements IObservable<Position> {
    private IRepository<Position, Integer> repo;
    protected List<IObserver<Position>> observers = new ArrayList<IObserver<Position>>();

    public PositionService(IRepository<Position, Integer> repo) {
        this.repo = repo;
    }

    public List<Position> getAll() {
        Iterable<Position> positionsRepo = repo.getAll();
        List<Position> positions = new ArrayList<>();

        for(Position p : positionsRepo) {
            positions.add(p);
        }

        return positions;
    }

    public void add(Position position) {
        try {
            repo.add(position);
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

    public void update(Position position) {
        try {
            repo.update(position);
            notifyObservers();
        }catch(ValidatorException e) {
            throw new RepositoryException(e);
        }
    }

    public void addObserver(IObserver<Position> o) {
        observers.add(o);
    }

    public void deleteObserver(IObserver<Position> o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for(IObserver<Position> o : observers) {
            o.update(getAll());
        }
    }
}
