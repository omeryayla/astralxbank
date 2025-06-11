package com.astralxbank.controller;

import com.astralxbank.entity.Transaction;
import com.astralxbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public Transaction deposit(@RequestParam UUID accountId, @RequestParam BigDecimal amount) {
        return transactionService.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestParam UUID accountId, @RequestParam BigDecimal amount) {
        return transactionService.withdraw(accountId, amount);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam UUID fromAccountId, @RequestParam UUID toAccountId, @RequestParam BigDecimal amount) {
        return transactionService.transfer(fromAccountId, toAccountId, amount);
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getAccountTransactions(@PathVariable UUID accountId) {
        return transactionService.getAccountTransactions(accountId);
    }
}
