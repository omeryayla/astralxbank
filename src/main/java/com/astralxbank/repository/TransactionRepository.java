package com.astralxbank.repository;

import com.astralxbank.entity.Account;
import com.astralxbank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByFromAccount(Account account);
}
