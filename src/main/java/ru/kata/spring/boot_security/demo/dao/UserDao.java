package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> listUsers();
    void update(User user);
    User getUser(int id);
    void delete(int id);
    User findUserByName(String name);
}
