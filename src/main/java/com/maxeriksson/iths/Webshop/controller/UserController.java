package com.maxeriksson.iths.Webshop.controller;

import com.maxeriksson.iths.Webshop.domain.user.User;
import com.maxeriksson.iths.Webshop.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired UserService service;

    private String viewPackage = "users/";
    private String registrationView = viewPackage + "register";

    @GetMapping("/")
    public String getLoginView(Model model) {
        model.addAttribute("user", new User());

        return registrationView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(
            ModelMap model, @Valid User user, BindingResult bindingResult) {
        String view = "redirect:/products";

        ArrayList<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errors);

            view = registrationView;
        } else {
            try {
                service.registerNewUser(user);
            } catch (Exception e) {
                errors.add(e.getMessage());
                model.addAttribute("errors", errors);

                view = registrationView;
            }
        }

        return new ModelAndView(view, model);
    }
}
