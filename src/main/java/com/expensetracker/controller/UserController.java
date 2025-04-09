package com.expensetracker.controller;

import com.expensetracker.dto.AcknowledgemntDto;
import com.expensetracker.dto.UserDto;
import com.expensetracker.entity.Account;
import com.expensetracker.entity.User;
import com.expensetracker.service.EmailService;
import com.expensetracker.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid BindingResult result, @RequestBody UserDto userDto){

        if(result.hasErrors()){
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errors.toString());
        }
        AcknowledgemntDto dto = userService.register(userDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<?> getAccountDetails(@AuthenticationPrincipal User user){

        List<Account> accounts = userService.getAccounts(user);

        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }
}
