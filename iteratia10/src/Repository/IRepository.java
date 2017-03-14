package Repository;

public interface IRepository<E, ID> {
    Iterable<E> getAll();
    E getItem(ID id);
    void add(E entity);
    void delete(ID id);
    void update(E entity);
}
