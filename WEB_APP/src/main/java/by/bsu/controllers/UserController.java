package by.bsu.controllers;

import by.bsu.daos.ProductDAO;
import by.bsu.daos.UserDAO;
import by.bsu.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    @Qualifier("UserDAO")
    UserDAO userDAO;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public @ResponseBody
    String insertAll(ModelMap model) {

        User user1 = new User();
        user1.set_id("5a2701b259687fd47309588d");
        user1.setEmail("john@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Morris");
        user1.setPassword("12345");
        user1.setAddress("Babaevskaya street, house 54, apartment 113");
        user1.setPhone("023(60)718-85-25");
        userDAO.update(user1);

        User user2 = new User();
        user2.set_id("5a2701b359687fd47309588e");
        user2.setEmail("neal@gmail.com");
        user2.setFirstName("Veronica");
        user2.setLastName("Neal");
        user2.setPassword("32145");
        user2.setAddress("Spring street, house 30, apartment 41");
        user2.setPhone("8(463)151-10-04");
        userDAO.update(user2);

        return "";
    }

}
