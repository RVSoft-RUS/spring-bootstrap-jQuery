package ru.rvs.springbootcrud.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rvs.springbootcrud.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean addUser(User user);

    List<User> getAllUsers();

    boolean deleteUser(long id);

    User getUserById(long id);

    boolean updateUser(User user);

    User getUserByLogin(String login);

    boolean isExistLogin(String login);
}
