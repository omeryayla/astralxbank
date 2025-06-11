package com.astralxbank.service;

import com.astralxbank.entity.Account;
import com.astralxbank.entity.User;
import com.astralxbank.repository.AccountRepository;
import com.astralxbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Account createAccount() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .user(user)
                .iban(generateIban())
                .balance(BigDecimal.ZERO)
                .build();

        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser(user);
    }

    private String generateIban() {
        // Basit örnek IBAN üretimi → gerçek hayatta daha karmaşık olur
        return "TR" + UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
    }
}
