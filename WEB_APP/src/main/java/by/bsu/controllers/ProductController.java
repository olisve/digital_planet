package by.bsu.controllers;

import by.bsu.daos.CategoryDAO;
import by.bsu.daos.ProductDAO;
import by.bsu.entities.Category;
import by.bsu.entities.Product;
import by.bsu.services.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("ProductService")
    ProductService productService;

    @Autowired
    @Qualifier("ProductDAO")
    ProductDAO productDAO;

    @Autowired
    @Qualifier("CategoryDAO")
    CategoryDAO categoryDAO;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody String getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        List<Category> categoriesList = productService.getCategoriesList();

        productList.get(0).setCategory(categoriesList.get(1));
        productList.get(1).setCategory(categoriesList.get(0));
        productList.get(2).setCategory(categoriesList.get(0));
        productList.get(3).setCategory(categoriesList.get(1));
        productDAO.update(productList.get(0));
        productDAO.update(productList.get(1));
        productDAO.update(productList.get(2));
        productDAO.update(productList.get(3));
        String json = new Gson().toJson(productList);
        return json;
    }

    @RequestMapping(value = "/products_by_category", method = RequestMethod.GET)
    public @ResponseBody String getProductsByCategory(@ModelAttribute(value="category") String category) {
        List<Product> productList = productService.getProductsByCategory(category.toLowerCase());
        String json = new Gson().toJson(productList);
        return json;
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public @ResponseBody String getProductById(HttpServletRequest request,
                                               @ModelAttribute(value="id") String id) {
        Product product = productService.findProductById(id);
        String json = new Gson().toJson(product);
        return json;
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getImage(@ModelAttribute(value="url") String url)  {
        try {
            InputStream is = this.getClass().getResourceAsStream("/images/"  + url);
            BufferedImage img = ImageIO.read(is);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/get_categories", method = RequestMethod.GET)
    public @ResponseBody String getCategoriesList() {
        List<Category> categoriesList = productService.getCategoriesList();
        String json = new Gson().toJson(categoriesList);
        return json;
    }

  /*  @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public @ResponseBody String insertAll(ModelMap model) {

        List<Product> productList = productService.getAllProducts();

        productList.get(0).setBrand("Xiaomi");
        productList.get(0).setImagePath("mobile/XiaomiRedmi4X16GBBlack.jpg");
        productDAO.update(productList.get(0));

        productList.get(1).setBrand("Samsung");
        productList.get(1).setImagePath("mobile/SamsungGalaxyS8DualSIM64GB.jpg");
        productDAO.update(productList.get(1));

        productList.get(2).setBrand("Lenovo");
        productList.get(2).setImagePath("tablet/LenovoTab2A10-70F16GBBlue.jpg");
        productDAO.update(productList.get(2));

        productList.get(3).setBrand("Apple");
        productList.get(3).setImagePath("tablet/AppleiPad32GBSilver.jgp");
        productDAO.update(productList.get(3));
        /*AbstractDAO<Category> categoryAbstractDAO = new AbstractDAO<Category>(Category.class);

        List<Category> categories = categoryAbstractDAO.readAll();

        Product tab1 = new Product();
        tab1.setCategory(categories.get(1));
        tab1.setPrice(320);
        tab1.setDescription("10.1\" IPS (1920x1200), Android, RAM 2GB, flash memory 16 GB, color blue/black");
        Map<String, String> map1 = new LinkedHashMap();
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
        Map<String, String> map2 = new LinkedHashMap();
        map2.put("Version of the operating system", "iOS 10");
        map2.put("Screen size", "9.7\"");
        map2.put("Screen resolution", "2048x1536");
        map2.put("RAM", "2 GB");
        map2.put("Flash memory", "32 GB");
        tab2.setCharacteristics(map2);
        tab2.setName("Apple iPad 32GB Silver");

        productDAO.create(tab2);*/

    //    return "";
    //}*/
}
