package by.bsu.daos;

import by.bsu.entities.Mark;
import by.bsu.utils.MongoOperationSupplier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MarkDAO")
public class MarkDAO extends AbstractDAO<Mark> {

    public List<Mark> readAll() {
        MongoOperations mo = new MongoOperationSupplier().get();
        List<Mark> list = mo.findAll(Mark.class);
        return list;
    }
}
