package ru.rvs.springbootcrud.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.rvs.springbootcrud.model.User;

import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @GetMapping("/user")
    public ModelAndView userGet(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/userInfo");
        mv.addObject("user",auth.getPrincipal());
        mv.addObject("userEmail",((User) auth.getPrincipal()).getLogin());
        mv.addObject("rolesAuth",((User) auth.getPrincipal()).getRoles()
                .stream().map(Objects::toString).collect(Collectors.joining(", ")));
        return mv;
    }
}
