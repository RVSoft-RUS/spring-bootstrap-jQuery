package ru.rvs.springbootcrud.repository;

import org.springframework.stereotype.Repository;
import ru.rvs.springbootcrud.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRolesByName(String name) {
        TypedQuery<Role> query = entityManager
                .createQuery("select r from Role r WHERE r.name= :name", Role.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findAny().orElse(null);
    }
}
