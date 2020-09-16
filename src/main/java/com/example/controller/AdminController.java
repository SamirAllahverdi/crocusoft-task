package com.example.controller;
import com.example.model.User;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@Log4j2
public class AdminController {

    private final UserService userService;

    @GetMapping("/main-page-admin")
    public String get(Principal p, Model model) {
        model.addAttribute("users", userService.findAll());

        writeFullNameToModel(p, model);

        return "main-page-admin";
    }

    @PostMapping("/main-page-admin")
    public String post(@ModelAttribute("txt") String txt, Principal p, Model model) {
        List<User> userList = userService.findByNameContainsIgnoreCase(txt);
        model.addAttribute("users", userList);

        writeFullNameToModel(p, model);

        return "main-page-admin";
    }

    @GetMapping("/main-page-admin-country/{country}")
    public String postSearchByCountry(@PathVariable String country, Principal p, Model model) {
        model.addAttribute("users", userService.findAllByCountry(country));

        writeFullNameToModel(p, model);

        return "main-page-admin";
    }


    private void writeFullNameToModel(Principal p, Model model) {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) p;
        User user = (User) userToken.getPrincipal();

        model.addAttribute("fullName", user.getFullName());
    }
}
