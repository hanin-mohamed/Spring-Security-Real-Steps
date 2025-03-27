package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
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
}
