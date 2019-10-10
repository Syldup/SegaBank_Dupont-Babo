package dal;

import java.io.IOException;
import java.sql.SQLException;

public interface IDAO<ID, T> {
    public void create(T object);
    public void update(T object);
    public T[] findAll();
    public T findById(ID id);
    public void deleteById(ID id);
}
