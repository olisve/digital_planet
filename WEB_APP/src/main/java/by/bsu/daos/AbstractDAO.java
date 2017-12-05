package by.bsu.daos;

import by.bsu.utils.MongoOperationSupplier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractDAO <T> {

    private final Class<T> currentClass;

    public AbstractDAO(){
        this.currentClass = (Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public AbstractDAO(Class<T> tClass){
        this.currentClass = tClass;
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

    public T findById(String id){
        MongoOperations mo = new MongoOperationSupplier().get();
        Query searchUserQuery = new Query(Criteria.where("_id").is(id));
        T entity = mo.findOne(searchUserQuery, currentClass);
        return entity;
    }

}
