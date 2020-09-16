package com.example.controller;


import com.example.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/main-page-user")
public class UserController {

    @GetMapping
    public String get(Model model, Principal p){

        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) p ;
        User user = (User) userToken.getPrincipal();

        model.addAttribute("fullName", user.getFullName());
        return "main-page-user";
    }

}
