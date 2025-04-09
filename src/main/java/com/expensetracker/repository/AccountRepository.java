package com.expensetracker.repository;

import com.expensetracker.entity.Account;
import com.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {


    List<Account> findALLByUser(User user);
}
