package com.astralxbank.repository;

import com.astralxbank.entity.Account;
import com.astralxbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUser(User user);
}
