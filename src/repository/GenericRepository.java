package repository;

import contract.Identifiable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericRepository<T extends Identifiable> implements Repository<T> {
    private final Map<Integer, T> storage = new HashMap<>();

    @Override
    public void add(T item) {
        if (item != null) {
            storage.put(item.getId(), item);
        }
    }

    @Override
    public T getById(int id) {
        return storage.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void addAll(List<? extends T> items) {
        if (items != null) {
            for (T item : items) {
                add(item);
            }
        }
    }

    @Override
    public boolean removeById(int id) {
        return storage.remove(id) != null;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
