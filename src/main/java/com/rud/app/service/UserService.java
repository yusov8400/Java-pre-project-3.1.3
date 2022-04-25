package com.rud.app.service;

import com.rud.app.DAO.RoleDao;
import com.rud.app.DAO.UserDao;
import com.rud.app.model.Role;
import com.rud.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    private final UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public List<User> index() {
        return userDao.index();
    }


    @Transactional
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }


    @Transactional
    public void save(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(14L));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional
    public void update(long id, User updatedUser) {
        Set<Role> roles = new HashSet<>();
        updatedUser.setRoles(roles);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userDao.update(id, updatedUser);
    }

    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }


    @Transactional
    public User getUserByUsername(String userName) {
        return userDao.getUserByUsername(userName);
    }


    @Transactional()
    public List<Role> getAll() {
        return roleDao.getAll();
    }
}
