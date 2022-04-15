package com.rud.app.service;

import com.rud.app.DAO.UserDao;
import com.rud.app.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Transactional
    public List<User> index() {
        return userDao.index();
    }


    @Transactional
    public User show(long id) {
        return userDao.show(id);
    }


    @Transactional
    public void save(User user) {
        userDao.save(user);

    }


    @Transactional
    public void update(long id, User updatedUser) {
        userDao.update(id, updatedUser);

    }

    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }
}
