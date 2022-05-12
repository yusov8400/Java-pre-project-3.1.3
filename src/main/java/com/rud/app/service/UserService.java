package com.rud.app.service;

import com.rud.app.model.Role;
import com.rud.app.model.User;

import java.util.List;

public interface UserService {
List<User> index();

User save(User user);

User update(User updatedUser);

void delete(Long id);

User findByUserName(String username);

}
