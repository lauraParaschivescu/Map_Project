package Repository;
import Domain.IBaseEntity;
import Exception.CustomException;
import java.util.ArrayList;
import java.util.*;
import Validator.*;

public abstract class AbstractRepository<E extends IBaseEntity<ID>, ID> implements IRepository<E, ID> {
    protected List<E> entities;
    protected IValidator<E> val;

    // Constructor
    public AbstractRepository(IValidator<E> val)
    {
        this.val = val;
        entities = new ArrayList<E>();
    }

    // Verificarea unicitatii elementelor in functie de cheia lor primara care este ID-ul
    private void unique(E entity)
    {
        for(E item : entities)
        {
            if(item.getId().equals(entity.getId()))
               throw new CustomException("\nid mai exista in baza noastra de date!\n");
        }
    }

    // Adaugarea unei entitati
    public void add(E entity)
    {
        try
        {
            val.validate(entity);
            unique(entity);
        }
        catch (ValidatorException e)
        {
            throw new RepositoryException(e);
        }

        entities.add(entity);
    }

    // Intoarcerea vectorului cu entitati
    public Iterable<E> getAll() {
        return entities;
    }

    public E getItem(ID id)
    {
        try
        {
            E entity = entities.stream().filter(x -> x.getId() == id).findFirst().get();
            if(entity.getId().equals(id))
                return entity;
        }
        catch(Exception e)
        {
            throw new CustomException("\nid nu exista in baza noastra de date!\n");
        }

        return null;
    }

    // Stergere entitate
    public void delete(ID id)
    {
        boolean removed = entities.removeIf(item -> item.getId().equals(id));

        if(!removed)
            throw new CustomException("\n Elementul cu id dat nu poate fi sters!\n");
    }

    public void update(E entity)
    {
        try {
            val.validate(entity);
            entities.set(entities.indexOf(entities.stream().filter(x -> x.getId() == entity.getId()).findFirst().get()), entity);
        }
        catch (ValidatorException e)
        {
            throw new RepositoryException(e);
        }
    }
}
