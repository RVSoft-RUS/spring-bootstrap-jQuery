package ru.rvs.springbootcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rvs.springbootcrud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUsersByLogin(String login);
}
