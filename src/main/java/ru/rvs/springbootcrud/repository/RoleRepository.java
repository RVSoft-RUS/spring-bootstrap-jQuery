package ru.rvs.springbootcrud.repository;

import ru.rvs.springbootcrud.model.Role;

public interface RoleRepository {
    Role getRolesByName(String name);
}
