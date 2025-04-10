package com.expensetracker.service;


import com.expensetracker.dto.AccountRequestDTO;
import com.expensetracker.entity.Account;
import com.expensetracker.entity.User;
import com.expensetracker.repository.AccountRepository;
import com.expensetracker.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

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

    public BigDecimal getTotal(User user) {

        List<Account> accounts = accountRepository.findALLByUser(user);


        BigDecimal  total = accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);

        return total;

    }

    public List<Account> fetchaccounts(User user) {

        return accountRepository.findALLByUser(user);

    }

    public String deleteAccount(Long id, User user) {

        Optional<Account> opAccount = accountRepository.findById(id);

        if(opAccount.isPresent()){
            Account account = opAccount.get();

            if(account.getUser().getId()== user.getId()){
                accountRepository.deleteById(id);
                return "Account Deleted Successfully";
            }

            return "Access Denied";
        }

        return "Account doesn't exist";


    }
}
