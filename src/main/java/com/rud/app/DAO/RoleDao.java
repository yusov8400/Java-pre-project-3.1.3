package com.rud.app.DAO;

import com.rud.app.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDao {
    @PersistenceContext
    private EntityManager entityManager;


    public Role save(Role role) {
        List<Role> roles = getAll();
        String name = role.getRole();
        boolean containRole = roles.stream().map(Role::getRole).anyMatch(name::equals);
        if (!containRole) {
            role = new Role(name);
            entityManager.persist(role);
        }
        return role;
    }

    @SuppressWarnings("unchecked")
    public Role getByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.role = :name", Role.class);
        return query.setParameter("name", name).getSingleResult();
    }


    public Role getById(Long id) {
        return entityManager.find(Role.class, id);
    }


    @SuppressWarnings("unchecked")
    public List<Role> getAll() {
        return entityManager.createQuery("FROM Role").getResultList();
    }

}

