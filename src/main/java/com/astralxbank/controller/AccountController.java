package com.astralxbank.controller;

import com.astralxbank.entity.Account;
import com.astralxbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public Account createAccount() {
        return accountService.createAccount();
    }

    @GetMapping("/my-accounts")
    public List<Account> getUserAccounts() {
        return accountService.getUserAccounts();
    }
}
