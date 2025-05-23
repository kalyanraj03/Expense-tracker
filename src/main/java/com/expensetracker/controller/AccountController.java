package com.expensetracker.controller;

import com.expensetracker.dto.AccountRequestDTO;
import com.expensetracker.entity.Account;
import com.expensetracker.entity.User;
import com.expensetracker.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/add")
    public ResponseEntity<?> creatAccount(@Valid @RequestBody AccountRequestDTO dto, BindingResult result ,
                                          @AuthenticationPrincipal User user){
        if(result.hasErrors()){
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }

        String staus = accountService.createAccount(dto, user);


        return new ResponseEntity<>(staus, HttpStatus.CREATED);
    }


    @GetMapping("/balance/total")
    public ResponseEntity<?> getTotalBalance(@AuthenticationPrincipal User user){

        BigDecimal total = accountService.getTotal(user);

        return new ResponseEntity<>(total, HttpStatus.OK);

    }

    @GetMapping("/allaccounts")
    public ResponseEntity<?> getAllAccounts(@AuthenticationPrincipal User user){

        List<Account> accounts = accountService.fetchaccounts(user);

        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id, @AuthenticationPrincipal User user){
        String s = accountService.deleteAccount(id, user);
        return ResponseEntity.status(200).body(s);
    }




}
