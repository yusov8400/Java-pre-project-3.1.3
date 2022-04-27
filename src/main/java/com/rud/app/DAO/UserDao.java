package com.rud.app.DAO;

import com.rud.app.model.Role;
import com.rud.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;


    public User getUserById(long id) {
        User user = em.find(User.class, id);
        return user;
    }

    @SuppressWarnings("unchecked")
    public User getUserByUsername(String userName) {
        TypedQuery<User> query = em.createQuery("from User user where user.username = :userName", User.class);
        query.setParameter("userName", userName);
        Optional<User> userFromDbByUserName = Optional.of(query.getSingleResult());
        return userFromDbByUserName.orElse(new User());
    }

    @SuppressWarnings("unchecked")
    public List<User> index() {
        TypedQuery<User> query = em.createQuery("from User", User.class);
        List<User> allUsers = query.getResultList();
        return allUsers;
    }

    public void save(User users) {
        em.persist(users);
    }

    public void update(long id, User updatedUsers) {
        em.merge(updatedUsers);
    }

    public void delete(long id) {
        em.remove(em.find(User.class, id));
    }
}
