package com.bankingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    
    @GetMapping("/")
    public String homePage(Model model) {
        return "home";  // This will map to a Thymeleaf template called "home.html"
    }
}
