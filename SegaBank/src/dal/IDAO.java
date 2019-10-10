package dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDAO<ID, T> {
    public void create(T object);
    public void update(T object);
    public List<T> findAll();
    public T findById(ID id);
    public void deleteById(ID id);
}
