package com.icici.demo.controllers;


import org.springframework.web.bind.annotation.RestController;

import com.icici.demo.entities.Users;
import com.icici.demo.repos.AccountsRepository;
import com.icici.demo.repos.UserRepository;
import com.icici.demo.entities.Accounts;


import lombok.extern.slf4j.Slf4j;

//import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AccountsRepository accountRepository;
    @GetMapping("/users")
    public List<Users> fetchAllTrips() {
        // logic to fetch from DB
        return userRepository.findAll();
    }
    @GetMapping("/user/{userId}/accounts")
    List<Accounts> fetchAllAccountsForUser(@PathVariable("userId") int userId){
        Optional<Users> userFound = userRepository.findById(userId);
        if (userFound.isPresent()) {
            Users user=userFound.get();
            log.debug("User Found " + userFound.get());
            return user.getAccounts();
        } else {
            log.warn("User Not Found with id " + userId);
            throw new UserNotFoundException("Trip not found with id " + userId);
        }
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    @GetMapping("/users/{id}")
    public Users fetchAUser(@PathVariable("id") int id) {
        Optional<Users> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            log.debug("User Found " + userFound.get());
            return userFound.get();
        } else {
            log.warn("User Not Found with id " + id);
            throw new UserNotFoundException("Trip not found with id " + id);
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUsers(@RequestBody Users users) {
        userRepository.save(users);
    }
    
    @PostMapping("/users/{id}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccountstoUser(@PathVariable("id")int id,@RequestBody Accounts accounts) {
        Optional<Users> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            accountRepository.save(accounts);
            Users user = userFound.get();
            List<Accounts> accountList= user.getAccounts();
            accountList.add(accounts);
            user.setAccounts(accountList);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Trip not found with id " + id);
        }

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // @PostMapping("/users/{id}/accounts")
    // public void addAccountToUser(@PathVariable("id") int id, @RequestBody Accounts accounts) {
    //     Optional<Users> userFound = userRepository.findById(id);
    //     if (userFound.isPresent()) {
    //         Users user = userFound.get();
    //         List<Accounts> account = user.getAccounts();
    //         accounts.add(accounts);
    //         user.setAccounts(accounts);
    //         userRepository.save(user);
    //     } else {
    //         throw new AccountNotFoundException("Account not found with id " + id);
    //     }
    // }
    

}

