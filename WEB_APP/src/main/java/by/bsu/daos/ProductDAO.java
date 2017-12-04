package by.bsu.daos;

import by.bsu.entities.Category;
import by.bsu.entities.Product;
import by.bsu.utils.MongoOperationSupplier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductDAO")
public class ProductDAO extends AbstractDAO<Product> {

    public List<Product> findByCategory(Category category) {
        MongoOperations mo = new MongoOperationSupplier().get();
        Query searchUserQuery = new Query(Criteria.where("category").is(category));
        List<Product> productList = mo.find(searchUserQuery, Product.class);
        return productList;
    }
}
