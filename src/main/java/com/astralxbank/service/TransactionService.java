package com.astralxbank.service;

import com.astralxbank.entity.*;
import com.astralxbank.repository.AccountRepository;
import com.astralxbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private Account getAuthenticatedUserAccount(UUID accountId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized account access");
        }

        return account;
    }

    @Transactional
    public Transaction deposit(UUID accountId, BigDecimal amount) {
        Account account = getAuthenticatedUserAccount(accountId);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .fromAccount(account)
                .toAccount(null)
                .amount(amount)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(UUID accountId, BigDecimal amount) {
        Account account = getAuthenticatedUserAccount(accountId);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .fromAccount(account)
                .toAccount(null)
                .amount(amount)
                .transactionType(TransactionType.WITHDRAW)
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
        Account fromAccount = getAuthenticatedUserAccount(fromAccountId);
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .transactionType(TransactionType.TRANSFER)
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAccountTransactions(UUID accountId) {
        Account account = getAuthenticatedUserAccount(accountId);
        return transactionRepository.findByFromAccount(account);
    }
}
