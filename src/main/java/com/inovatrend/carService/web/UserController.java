package com.inovatrend.carService.web;

import com.inovatrend.carService.domain.Permission;
import com.inovatrend.carService.domain.User;
import com.inovatrend.carService.service.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@SessionAttributes("user")
@RequestMapping("/user")
public class UserController {



    private final UserManager userManager;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserManager userManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/info/{userId}")
    public String clientInfo(Model model, @PathVariable Long userId) {

        Optional<User> user = userManager.getUser(userId);
        if(user.isPresent()) {
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
                new User(null, "", "",  true, permissions);
        model.addAttribute("user", user);
        return "create-user";
    }


    @PostMapping("/create")
    public String processCreateUserForm(Model model, @ModelAttribute @Valid User user, BindingResult result){

        if(result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("user", user);
            return "create-user";
        }
        else {
            // save to DB
            user.setPassword(  passwordEncoder.encode(user.getPassword()));
            User savedUser = userManager.save(user);
            model.addAttribute("user", savedUser);
            return "redirect:/user/list";
        }
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userManager.deleteUser(userId);
        return "redirect:/user/list";
    }

    @GetMapping("/edit/{userId}")
    public String editClient( Model model, @PathVariable Long userId) {
        Optional<User> user = userManager.getUser(userId);

        model.addAttribute("user", user);
        return "create-user";

    }

    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public User createUserRest(@RequestBody User user) {
        return user;
    }
}
