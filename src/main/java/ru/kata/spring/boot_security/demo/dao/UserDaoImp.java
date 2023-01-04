package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
@EnableTransactionManagement
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public void add(User user) {
        entityManagerFactory.persist(encryptPassword(user));
    }

    @Override
    public void update(User user) {;
        entityManagerFactory.merge(encryptPassword(user));
    }

    @Override
    public List<User> listUsers() {
        return entityManagerFactory.createNativeQuery("SELECT * FROM test1.users",User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManagerFactory.find(User.class, (long) id);
    }

    @Override
    public void delete(int id) {
        entityManagerFactory.remove(getUser(id));
    }

    @Override
    public User findUserByName(String name) {
        String query = String.format("SELECT * FROM test1.users where test1.users.name = %s", name);
        List<User> users = entityManagerFactory.createNativeQuery(query, User.class).getResultList();
        if (users.isEmpty()){
            throw new UsernameNotFoundException(String.format("User with name %s not found", name));
        }
        return users.get(0);
    }

    private User encryptPassword (User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}