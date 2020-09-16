package com.example.controller;

import com.example.enums.COUNTRY;
import com.example.model.User;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@AllArgsConstructor
@Log4j2
public class ModificationController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @ModelAttribute("emptyUser")
    public User user() {
        return new User();
    }

    @ModelAttribute("countries")
    public List<COUNTRY> addCurrenciesToModel(Model model) {

        List<COUNTRY> collect = Arrays.stream(COUNTRY.values()).collect(Collectors.toList());
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
    public String postEdit(User user, BindingResult result) {

        log.info("USER IN EDIT " + user);
        if (hasErrorEdit(user.getId(), user.getEmail(), result)) {
            return "form-edit";
        }

        userService.save(user);
        return "redirect:/main-page-admin";
    }


    @GetMapping("/add")
    public String getAdd() {
        return "form-add";
    }

    @PostMapping("/add")
    public String postAdd(@Valid @ModelAttribute("emptyUser") User user, BindingResult result) {

        if (hasErrorAdd(user.getEmail(), result)) {
            return "form-add";
        }
//        TO WHAT EXTENT, IT IS CORRECT?
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);

        return "redirect:/main-page-admin";
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id) {

        userService.delete(id);

        return new RedirectView("/main-page-admin");
    }

    private boolean hasErrorAdd(String email, BindingResult result) {
        if (result.hasErrors() && userService.findByEmail(email).isPresent()) {
            result.rejectValue("email", "user.email.exists", "There is already an account registered with that email");
            log.info("both");
            return true;
        } else if (result.hasErrors()) {
            log.info("password");
            return true;
        } else if (userService.findByEmail(email).isPresent()) {
            result.rejectValue("email", "user.email.exists", "There is already an account registered with that email");
            log.info("email");
            return true;
        } else {
            return false;
        }
    }

    private boolean hasErrorEdit(long id, String email, BindingResult result) {

        if (!email.equals(userService.findById(id).getEmail()) && userService.findByEmail(email).isPresent()) {
            result.rejectValue("email", "user.email.exists", "There is already an account registered with that email");
            return true;
        } else {
            return false;
        }

    }
}
