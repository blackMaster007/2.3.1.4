package ru.kata.spring.boot_security.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean add(User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
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
    public User save(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setLastName(user.getLastName());
        newUser.setAge(user.getAge());

        if (user.getRoles().isEmpty()){
            newUser.addRole(getRole("ROLE_USER"));
        } else {
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                newUser.addRole(getRole(role.getName()));
            }
        }

        if (user.getId() == null) {
            newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            entityManager.persist(newUser);
        } else {
            newUser.setId(user.getId());

            if (user.getPassword()==null) {
                newUser.setPassword(showUser(user.getId()).getPassword());
            } else {
                newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            entityManager.merge(newUser);
            System.out.println(newUser);
            System.out.println(user);
        }
        return newUser;
    }

    @Override
    public User showUser(Long id) {
        return  entityManager.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public Role getRole(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.name = :name", Role.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(entityManager.createQuery("select r from Role r", Role.class).getResultList());
    }

}
