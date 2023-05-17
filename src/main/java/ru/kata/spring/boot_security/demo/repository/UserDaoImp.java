package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        this.entityManager.merge(user);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void remove(Long id) {
        entityManager.remove(showUser(id));
    }

    @Override
    public User update(Long id, User newUser) {
        User user = showUser(id);
        user.setUsername(newUser.getUsername());
        user.setLastName(newUser.getLastName());
        user.setAge(newUser.getAge());
        return user;
    }

    @Override
    public User showUser(Long id) {
        return  entityManager.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id).getSingleResult();
    }

}
