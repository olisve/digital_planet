package by.bsu.daos;

import by.bsu.entities.Category;
import by.bsu.utils.MongoOperationSupplier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository("CategoryDAO")
public class CategoryDAO extends AbstractDAO<Category>{

    public Category findByName(String name) {
        MongoOperations mo = new MongoOperationSupplier().get();
        Query searchUserQuery = new Query(Criteria.where("name").is(name));
        Category category = mo.findOne(searchUserQuery, Category.class);
        return category;
    }

}
