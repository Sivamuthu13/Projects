package com.bankingapp.controller;


import com.bankingapp.model.Transaction;
import com.bankingapp.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Display the transaction form
    @GetMapping("/form")
    public String showTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transaction-form"; // This will be the Thymeleaf template for the form
    }

    // Handle form submission to process the transaction
    @PostMapping("/process")
    public String processTransaction(
            @RequestParam Long accountId,
            @RequestParam double amount,
            @RequestParam String transactionType,
            @RequestParam String description,
            Model model) {

        // Call the service to process the transaction
        String result = transactionService.processTransaction(accountId, amount, transactionType, description);

        // Add the result message to the model to be displayed on the response page
        model.addAttribute("result", result);

        // Redirect to the result page to display the outcome
        return "transaction-result"; // This will be the Thymeleaf template for displaying the result
    }
}
