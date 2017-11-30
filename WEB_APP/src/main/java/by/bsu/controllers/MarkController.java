package by.bsu.controllers;

import by.bsu.entities.Product;
import com.google.gson.Gson;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class MarkController {

    /*@RequestMapping(value = "/getProductMark", method = RequestMethod.GET)
    public @ResponseBody
    String getProductMark(ModelMap model) {
        List<Product> productList = productService.getAllProducts();
        String json = new Gson().toJson(productList);
        return json;
    }*/
}
