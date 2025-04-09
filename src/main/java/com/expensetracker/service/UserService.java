package com.expensetracker.service;

import com.expensetracker.dto.AcknowledgemntDto;
import com.expensetracker.dto.LoginDto;
import com.expensetracker.dto.UserDto;
import com.expensetracker.entity.Account;
import com.expensetracker.entity.User;
import com.expensetracker.repository.AccountRepository;
import com.expensetracker.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    public AcknowledgemntDto register(UserDto userDto) {

        AcknowledgemntDto ack = new AcknowledgemntDto();

        User user = modelMapper.map(userDto, User.class);

        if(userRepository.findByMobile(user.getMobile()).isPresent()){
            ack.setError("Mobile Number already Exist");
            return ack;
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            ack.setError("Email id already exist");
            return ack;
        }

        String hashpwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4));

        user.setPassword(hashpwd);
        user.setRegisterdAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        emailService.sendEmail(savedUser.getEmail(), "Welcome to SmartExpense " ,
                "Hi "+savedUser.getName()+" you are successfully registerd to smartExpense application, now you can enjoy the services our application by login");


        ack.setStatus("Registration completed successfully");
        ack.setRegisteredAt(LocalDateTime.now());

        return ack;

    }

    public String loginUser(LoginDto dto) {

        Optional<User> opuser = userRepository.findByEmail(dto.getEmail());

        if(opuser.isPresent()){
            User user = opuser.get();

            if(BCrypt.checkpw(dto.getPassword(), user.getPassword())){
                String token = jwtService.generateToken(user.getEmail());

                return token;

            }
            else{
                return "Invalid UserName/Password, Please Try again later";
            }
        }
        return "Email does not exist, Please check again";


    }

    public List<Account> getAccounts(User user) {

        return accountRepository.findALLByUser(user);
    }
}
