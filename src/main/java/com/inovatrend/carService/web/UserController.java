package com.inovatrend.carService.web;

import com.inovatrend.carService.domain.Permission;
import com.inovatrend.carService.domain.User;
import com.inovatrend.carService.service.EmailManager;
import com.inovatrend.carService.service.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
@SessionAttributes("user")
@RequestMapping("/user")
public class UserController {


    private final UserManager userManager;
    private final EmailManager emailManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserManager userManager, EmailManager emailManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.emailManager = emailManager;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/info/{userId}")
    public String clientInfo(Model model, @PathVariable Long userId) {

        Optional<User> user = userManager.getUser(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "user-info";
    }

    @RequestMapping(value = "/list")
    public String listAllUsers(Model model, Pageable pageable) {

        Page<User> allUsers = userManager.getAllUsers(pageable);
        model.addAttribute("users", allUsers);
        return "list-users";
    }


    @GetMapping("/create")
    public String showCreateUserForm(Model model, @RequestParam(required = false) Long id) {

        ArrayList<Permission> permissions = new ArrayList<>();
        User user = id != null ? userManager.getUser(id).get() :
                new User(null, "", "","","","", false, permissions);
        model.addAttribute("user", user);
        return "create-user";
    }


    @PostMapping("/create")
    public String processCreateUserForm(Model model, @ModelAttribute @Valid User user, BindingResult result) {

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("user", user);
            return "create-user";
        } else {
            // save to DB
            if(user.getPassword().equals(user.getConfirmPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userManager.save(user);
                model.addAttribute("user", savedUser);
                return "redirect:/user/list";
            } else {
                return "create-user";
            }
        }
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userManager.deleteUser(userId);
        return "redirect:/user/list";
    }

    @GetMapping("/edit/{userId}")
    public String editUser(Model model, @PathVariable Long userId) {
        Optional<User> user = userManager.getUser(userId);
        if (user.isPresent())
            model.addAttribute("user", user.get());
        return "create-user";

    }

    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public User createUserRest(@RequestBody User user) {
        return user;
    }


    /* ------------------------------------------------------------------------------------------------

        Forgot password

   ---------------------------------------------------------------------------------------------------- */

    @GetMapping("/whichUser")
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("which-user");

    }

    @PostMapping(value = "/whichUser")
    public String processForgetPasswordPage(@RequestParam("email") String userEmail, Model model) {
        Optional<User> optional = userManager.findByEmail(userEmail);


        if (!optional.isPresent()) {
            model.addAttribute("errorMessage", "We didn't find an account for that e-mail address.");
            return "which-user";
        } else {
            User user = optional.get();

            user.setResetToken(UUID.randomUUID().toString());

            userManager.save(user);

            String appUrl= "http://localhost:9092/user/newPassword?token=" + user.getResetToken();
            model.addAttribute("appUrl", appUrl);


            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Password change");
            registrationEmail.setText("To change your password, please click the link below:\n"
                    + appUrl);
            registrationEmail.setFrom("smtp.gmail.com");

            emailManager.sendEmail(registrationEmail);

            return "which-user";
        }



    }

    @GetMapping("/newPassword")
    public String displayResetPasswordPage(Model model, @RequestParam("token") String token) {

        Optional<User> user = userManager.findUserByResetToken(token);

        if (user.isPresent()) { // Token found in DB
            model.addAttribute("user", user.get());
        } else { // Token not found in DB
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        return "new-password";

    }

    @PostMapping("/newPassword")
    public String setNewPassword(@ModelAttribute User user, Model model, BindingResult result) {



        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("user", user);
            return "new-password";
        } else {
            // save to DB
            if(user.getPassword().equals(user.getConfirmPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setResetToken(null);
                User savedUser = userManager.save(user);
                model.addAttribute("user", savedUser);
                return "redirect:/";
            } else {
                return "new-password";
            }
        }
    }





}