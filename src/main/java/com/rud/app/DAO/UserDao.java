package com.rud.app.DAO;

import com.rud.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public User getUserById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        User user = em.find(User.class, id);
        return user;
    }

    public User getUserByUsername(String userName) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("from User user where user.username = :userName", User.class);
        query.setParameter("userName", userName);

        Optional<User> userFromDbByUserName = Optional.of(query.getSingleResult());

        return userFromDbByUserName.orElse(new User());
    }

    public List<User> index() {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("from User", User.class);
        List<User> allUsers = query.getResultList();
        return allUsers;
    }

    public void save(User users) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(users);
        em.getTransaction().commit();
    }

    public void update(long id, User updatedUsers) {
        EntityManager em = entityManagerFactory.createEntityManager();

        User userToBeUpdated = em.find(User.class, id);

        em.getTransaction().begin();
        userToBeUpdated.setUsername(updatedUsers.getUsername());
        userToBeUpdated.setEmail(updatedUsers.getEmail());
        em.getTransaction().commit();
    }

    public void delete(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();

    }
}
