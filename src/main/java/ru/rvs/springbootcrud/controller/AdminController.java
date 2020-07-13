package ru.rvs.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.rvs.springbootcrud.model.Role;
import ru.rvs.springbootcrud.model.User;
import ru.rvs.springbootcrud.service.RoleService;
import ru.rvs.springbootcrud.service.UserService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
//    private String lastLogin;
    @Qualifier("userServiceImp")
    private final UserService userService;
    @Qualifier("roleServiceImp")
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("users", userService.getAllUsers());
        mv.addObject("authUser", (User) auth.getPrincipal());
        mv.addObject("userEmail",((User) auth.getPrincipal()).getLogin());
        mv.addObject("user", new User()); //для Thymeleaf нужно передать объект
        mv.addObject("rolesAuth",((User) auth.getPrincipal()).getRoles()
                .stream().map(Objects::toString).collect(Collectors.joining(", ")));
        mv.setViewName("admin/users");
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView addUserPost(@ModelAttribute("user") @Valid User user,
                                    @RequestParam(value = "rolesFromHtml") String roles) {
        ModelAndView mv = new ModelAndView();
        if (userService.isExistLogin(user.getLogin())) {
            mv.addObject("errorText", "User with same login already exist.");
            mv.setViewName("error");
            return mv;
        }
        user.setRoles(makeRoles(roles));
        if (!userService.addUser(user)) {
            mv.addObject("errorText", "Error while save user.");
            mv.setViewName("error");
            return mv;
        }
        mv.setViewName("redirect:/admin/users");
        return mv;
    }

    @PostMapping("/edit")
    public ModelAndView editUserPost(@ModelAttribute("userE") @Valid User user,
                                     @RequestParam(value = "rolesFromHtml") String roles) {
        ModelAndView mv = new ModelAndView();
        user.setRoles(makeRoles(roles));
        userService.updateUser(user);
        mv.setViewName("redirect:/admin/users");
        return mv;
    }

    @PostMapping("/delete/{id}")
    public String deleteUserPost(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    private Set<Role> makeRoles(String role){
        Set<Role> userRoles = new HashSet<>();
        if (role.equals("user")) {
            userRoles.add(roleService.getRoleByName("USER"));
        }
        if (role.equals("admin")) {
            userRoles.add(roleService.getRoleByName("ADMIN"));
        }
        if (role.equals("adminAndUser")) {
            userRoles.add(roleService.getRoleByName("ADMIN"));
            userRoles.add(roleService.getRoleByName("USER"));
        }
        return userRoles;
    }
}
