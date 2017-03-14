package Utils;
import Utils.IObserver;

public interface IObservable<E> {
    void addObserver(IObserver<E> o);
    void deleteObserver(IObserver<E> o);
    void notifyObservers();
}
