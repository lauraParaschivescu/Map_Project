package Utils;

import java.util.List;

public interface IObserver<E> {
    void update(List<E> list);
}
