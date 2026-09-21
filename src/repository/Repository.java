package repository;

import contract.Identifiable;
import java.util.List;

public interface Repository<T extends Identifiable> {
    void add(T item);
    T getById(int id);
    List<T> getAll();
    void addAll(List<? extends T> items);
    boolean removeById(int id);
    int size();
}
