package com.example.controller;

import com.example.enums.COUNTRY;
import com.example.enums.ROLE;
import com.example.model.User;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@AllArgsConstructor
@Log4j2
public class ModificationController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @ModelAttribute("countries")
    public List<COUNTRY> addCurrenciesToModel(Model model) {
        List<COUNTRY> collect = Arrays.stream(COUNTRY.values())
                .collect(Collectors.toList());
        model.addAllAttributes(collect);
        return collect;
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "form-edit";
    }

    @PostMapping("/edit")
    public RedirectView postEdit(User user) {
        userService.save(user);

        return new RedirectView("/main-page-admin");
    }


    @GetMapping("/add")
    public String getAdd() {
        return "form-add";
    }

    @PostMapping("/add")
    public RedirectView postAdd(User user) {


//        TO WHAT EXTENT, IT IS CORRECT?
        user.setRole(ROLE.USER.name());
        user.setPassword(encoder.encode(user.getPassword()));

        userService.save(user);

        return new RedirectView("/main-page-admin");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id) {
        userService.delete(id);
        return new RedirectView("/main-page-admin");
    }
}
