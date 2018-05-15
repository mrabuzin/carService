package com.inovatrend.carService.web;


import com.inovatrend.carService.domain.Permission;
import com.inovatrend.carService.domain.User;
import com.inovatrend.carService.service.UserManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@SessionAttributes("user")
@RequestMapping("/registration")
public class RegistrationController {

    private final UserManager userManager;


    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserManager userManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        ArrayList<Permission> permissions = new ArrayList<>();
        User user = new User(null, "", "","","","", true, permissions);
        model.addAttribute("user", user);
        return "registration";
    }


    @PostMapping("/register")
    public String processRegistrationForm(Model model, @ModelAttribute @Valid User user, BindingResult result) {

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("user", user);
            return "registration";
        } else {
            // save to DB
            if(user.getPassword().equals(user.getConfirmPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userManager.save(user);

                model.addAttribute("user", savedUser);
                return "redirect:/";
            } else {
                return "registration";
            }
        }
    }

}
