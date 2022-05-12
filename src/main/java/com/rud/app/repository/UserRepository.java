package com.rud.app.repository;

import com.rud.app.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM User u where u.username =:username")
    User getByUserName(String username);
}
