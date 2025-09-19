package Repisotory;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseRepository<T, ID> {
    public Map<ID, T> storage = new HashMap<>();
    protected List<T> entities = new ArrayList<>();

    public Optional<T> findById(ID id){
        return
                Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    public abstract void save(T entity);
    public abstract ID getId(T entity);
}




