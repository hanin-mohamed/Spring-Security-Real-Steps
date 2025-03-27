package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage(){
        return "homePage";
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login-page";
    }
}
