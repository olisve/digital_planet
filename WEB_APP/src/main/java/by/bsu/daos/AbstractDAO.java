package by.bsu.daos;

import by.bsu.utils.MongoOperationSupplier;
import org.springframework.data.mongodb.core.MongoOperations;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractDAO <T> {

    private final Class<T> currentClass;

    public AbstractDAO(){
        this.currentClass = (Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void create(T entity) {
        MongoOperations mo = new MongoOperationSupplier().get();
        mo.save(entity);
    }

    public List<T> readAll() {
        MongoOperations mo = new MongoOperationSupplier().get();
        List<T> list = mo.findAll(currentClass);
        return list;
    }

    public void update(T entity) {
        MongoOperations mo = new MongoOperationSupplier().get();
        mo.save(entity);
    }

    public void delete(T entity) {
        MongoOperations mo = new MongoOperationSupplier().get();
        mo.remove(entity);
    }

}
