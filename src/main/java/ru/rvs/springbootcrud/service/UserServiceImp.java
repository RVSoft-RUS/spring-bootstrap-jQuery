package ru.rvs.springbootcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rvs.springbootcrud.model.User;
import ru.rvs.springbootcrud.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
   private UserRepository userDao;
   private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

   @Autowired
   public void setUserDao(UserRepository userDao) {
      this.userDao = userDao;
   }

   public UserServiceImp() {
   }

   @Transactional
   @Override
   public boolean addUser(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.save(user);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> getAllUsers() {
      return userDao.findAll();
   }

   @Transactional
   @Override
   public boolean deleteUser(long id) {
      userDao.deleteById(id);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserById(long id) {
      return userDao.getOne(id);
   }

   @Transactional
   @Override
   public boolean updateUser(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.save(user);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserByLogin(String login) {
      User user = userDao.getUsersByLogin(login);
      return user;
   }

   @Transactional(readOnly = true)
   @Override
   public boolean isExistLogin(String login) {
      User user = userDao.getUsersByLogin(login);
      return (user != null);
   }

   @Transactional
   @Override
   public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      User user = getUserByLogin(s);
      if (user == null) {
         throw new UsernameNotFoundException("User not found");
      }
      return user;
   }
}
