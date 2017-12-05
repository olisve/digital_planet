package by.bsu.services;

import by.bsu.daos.CategoryDAO;
import by.bsu.daos.ProductDAO;
import by.bsu.entities.Category;
import by.bsu.entities.Product;
import by.bsu.utils.CategoryNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductService")
public class ProductService {

    @Autowired
    @Qualifier("ProductDAO")
    ProductDAO productDAO;

    @Autowired
    @Qualifier("CategoryDAO")
    CategoryDAO categoryDAO;

    public List<Product> getAllProducts(){
        return productDAO.readAll();
    }

    public List<Product> getProductsByCategory(String category){
        CategoryNames categoryName = CategoryNames.valueOf(category);
        Category categoryObj = categoryDAO.findByName(categoryName.getName());
        return productDAO.findByCategory(categoryObj);
    }

    public Product findProductById(String id){
        return productDAO.findById(id);
    }

}
