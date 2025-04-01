package com.app.controller;

import com.app.model.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showHomePage(){
        return "home-page";
    }

    @GetMapping("/loginPage")
    public String showLoginPage(){
        return "login-page";
    }

    @GetMapping("/system")
    public String showSystemPage(){
        return "system-page";
    }

    @GetMapping("/accessDenied")
    public String showAccessDeniedPage(){
        return "access-denied";
    }



    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register-page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model){
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "Passwords don't match");
            return "register-page";
        }
        userService.register(user);
        return "redirect:/loginPage";
    }
}
