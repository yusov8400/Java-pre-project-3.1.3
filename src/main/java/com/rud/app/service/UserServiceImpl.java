package com.rud.app.service;

import com.rud.app.model.User;
import com.rud.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class UserServiceImpl implements UserService , UserDetailsService {
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> index() {
        return userRepository.findAll();
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }


    public User save(User user) {
        userRepository.save(user);
        return user;
    }


    public User update(User updatedUser) {
        userRepository.save(updatedUser);
        return updatedUser;
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}