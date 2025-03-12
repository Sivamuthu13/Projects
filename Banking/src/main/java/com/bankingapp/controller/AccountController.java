package com.bankingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bankingapp.model.Account;
import com.bankingapp.service.AccountService;


@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/form")
    public String showAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "account";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute Account account) {
        accountService.createAccount(account);
        return "redirect:/account/form";
    }
}
