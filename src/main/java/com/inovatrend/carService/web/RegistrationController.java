package com.inovatrend.carService.web;


import com.inovatrend.carService.domain.Permission;
import com.inovatrend.carService.domain.User;
import com.inovatrend.carService.service.EmailManager;
import com.inovatrend.carService.service.UserManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
@SessionAttributes("user")
@RequestMapping("/registration")
public class RegistrationController {

    private final UserManager userManager;
    private final EmailManager emailManager;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserManager userManager, EmailManager emailManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.emailManager = emailManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        ArrayList<Permission> permissions = new ArrayList<>();
        User user = new User(null, "", "","","","", false, permissions);
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

                user.setResetToken(UUID.randomUUID().toString());
                User savedUser = userManager.save(user);

                model.addAttribute("user", savedUser);

                String appUrl= "http://localhost:9092/registration/activateAccount?token=" + user.getResetToken();
                model.addAttribute("appUrl", appUrl);


                SimpleMailMessage registrationEmail = new SimpleMailMessage();
                registrationEmail.setTo(user.getEmail());
                registrationEmail.setSubject("Registration Confirmation");
                registrationEmail.setText("To activate account, please click the link below:\n"
                        + appUrl);
                registrationEmail.setFrom("smtp.gmail.com");

                emailManager.sendEmail(registrationEmail);

                return "redirect:/";
            } else {
                return "registration";
            }
        }
    }

    @GetMapping("/activateAccount")
    public String displayAccountActivation(Model model, @RequestParam("token") String token) {

        Optional<User> user = userManager.findUserByResetToken(token);

        if (user.isPresent()) { // Token found in DB
            model.addAttribute("user", user.get());
            user.get().setActive(true);
            user.get().setResetToken(null);
            userManager.save(user.get());
        } else { // Token not found in DB
            model.addAttribute("errorMessage", "Oops!  This is an invalid email activation link.");
        }

        return "activate-account";
    }

//    @PostMapping("/activateAccount")
//    public String processAccountActivation(@ModelAttribute User user, Model model, BindingResult result) {
//
//
//        if (result.hasErrors()) {
//            for (ObjectError error : result.getAllErrors()) {
//                System.out.println(error.getDefaultMessage());
//            }
//            model.addAttribute("user", user);
//            return "registration";
//        } else {
//            user.setActive(true);
//            user.setResetToken(null);
//            userManager.save(user);
//            return "activate-account";
//        }
//    }

}
