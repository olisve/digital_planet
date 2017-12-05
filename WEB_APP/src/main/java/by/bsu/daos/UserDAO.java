package by.bsu.daos;

import by.bsu.entities.User;
import org.springframework.stereotype.Repository;

@Repository("UserDAO")
public class UserDAO extends AbstractDAO<User> {
}
