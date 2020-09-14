package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/default")
public class DefaultController {

    @GetMapping
    public RedirectView defaultAfterLogin(HttpServletRequest request) {
        return request.isUserInRole("ROLE_ADMIN") ?
                new RedirectView("/main-page-admin")
                : new RedirectView("/main-page-user");
    }

}
