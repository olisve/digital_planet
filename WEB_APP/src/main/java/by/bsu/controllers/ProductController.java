package by.bsu.controllers;

import by.bsu.daos.ProductDAO;
import by.bsu.entities.Product;
import by.bsu.services.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("ProductService")
    ProductService productService;

    @Autowired
    @Qualifier("ProductDAO")
    ProductDAO productDAO;

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public @ResponseBody String getAllProducts(HttpServletRequest request) {
        List<Product> productList = productService.getAllProducts();
        String json = new Gson().toJson(productList);
        return json;
    }

    @RequestMapping(value = "/getProductsByCategory", method = RequestMethod.GET)
    public @ResponseBody String getProductsByCategory(HttpServletRequest request,
                                                      @ModelAttribute(value="category") String category) {
        List<Product> productList = productService.getProductsByCategory(category);
        String json = new Gson().toJson(productList);
        return json;
    }

   /* @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public @ResponseBody String insertAll(ModelMap model) {


        AbstractDAO<Category> categoryAbstractDAO = new AbstractDAO<Category>(Category.class);

        List<Category> categories = categoryAbstractDAO.readAll();

        Product tab1 = new Product();
        tab1.setCategory(categories.get(1));
        tab1.setPrice(320);
        tab1.setDescription("10.1\" IPS (1920x1200), Android, RAM 2GB, flash memory 16 GB, color blue/black");
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("Version of the operating system", "Android 5.0 Lollipop, Android 4.4 KitKat");
        map1.put("Screen size", "10.1\"");
        map1.put("Screen resolution", "1920x1200");
        map1.put("RAM", "2 GB");
        map1.put("Flash memory", "16 GB");
        tab1.setCharacteristics(map1);
        tab1.setName("Lenovo Tab 2 A10-70F 16GB Blue");

        productDAO.create(tab1);

        Product tab2 = new Product();
        tab2.setCategory(categories.get(1));
        tab2.setPrice(790);
        tab2.setDescription("9.7\" IPS (2048x1536), iOS, flash memory 32 GB, color white/silver");
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("Version of the operating system", "iOS 10");
        map2.put("Screen size", "9.7\"");
        map2.put("Screen resolution", "2048x1536");
        map2.put("RAM", "2 GB");
        map2.put("Flash memory", "32 GB");
        tab2.setCharacteristics(map2);
        tab2.setName("Apple iPad 32GB Silver");

        productDAO.create(tab2);

        return "";
    }*/
}
