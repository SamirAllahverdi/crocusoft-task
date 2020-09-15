package com.example.controller;


import com.example.model.User;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@Log4j2
public class AdminController {

    private final UserService userService;



    @GetMapping("/main-page-admin")
    public String get(Model model, Principal p) {

        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) p;
        User user = (User) userToken.getPrincipal();

        model.addAttribute("users", userService.findAll());
        model.addAttribute("fullName", String.format("%s %s",user.getName(), user.getSurname()));

        return "main-page-admin";
    }

    @PostMapping("/main-page-admin")
    public String post(@RequestParam("txt") String txt, Model model) {
        List<User> userList = userService.findByNameContainsIgnoreCase(txt);
        model.addAttribute("users", userList);
        return "main-page-admin";
    }

    @GetMapping("/main-page-admin-country/{country}")
    public String postSearchByCountry(@PathVariable String country, Model model) {
        model.addAttribute("users", userService.findAllByCountry(country));
        return "main-page-admin";
    }
}
