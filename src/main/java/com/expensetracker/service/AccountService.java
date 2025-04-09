package com.expensetracker.service;


import com.expensetracker.dto.AccountRequestDTO;
import com.expensetracker.entity.Account;
import com.expensetracker.entity.User;
import com.expensetracker.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    public String createAccount(AccountRequestDTO dto, User user) {

        if(user!=null){
            Account account = modelMapper.map(dto, Account.class);
            account.setUser(user);
            account.setCreatedAt(LocalTime.now());

            Account savedAccount = accountRepository.save(account);

            return "Account added successfully";

        }

        return "Invalid Request";



    }
}
