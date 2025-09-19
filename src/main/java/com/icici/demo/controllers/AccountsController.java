package com.icici.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.icici.demo.dto.AccountInfoDTO;
import com.icici.demo.entities.Accounts;
import com.icici.demo.entities.Users;
import com.icici.demo.repos.AccountsRepository;
import com.icici.demo.repos.UserRepository;

import lombok.extern.slf4j.Slf4j;

//import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.icici.demo.dto.AccountInfoDTO;
import com.icici.demo.services.AccountsService;

@Slf4j
@RestController
@CrossOrigin
public class AccountsController {





    @Autowired
    AccountsRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    private final AccountsService accountsService;
    // Constructor injection (recommended)
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/all_accounts")
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    public List<AccountInfoDTO> fetchAllAccounts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestHeader("userId") int userId) {
        // logic to fetch from DB
        
        return accountsService.getAllAccounts(page, size);
    }


    @GetMapping("/accounts/is_exists/{accountId}")
    public boolean checkIfAccountExists(@PathVariable("accountId") long accountId) {
        boolean exists = accountRepository.findById(accountId).isPresent();
        System.out.println("Account exists: " + exists);
        return exists;
    }

    // @GetMapping("/accounts")
    // public List<Accounts> fetchAccounts(@RequestHeader("userId") int userId) {
    //     return userRepository.findById(userId).get().getAccounts();
    // }
    //     }
    //     user.getAccounts().stream()).toList();
    // }



    @GetMapping("/accounts")
    public List<Accounts> fetchAccounts(@RequestHeader("userId") int userId) {
        return userRepository.findById(userId).get().getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public Accounts fetchAAccount(@PathVariable("id") long id) {
        Optional<Accounts> accountFound = accountRepository.findById(id);
        if (accountFound.isPresent()) {
            log.debug("Account Found " + accountFound.get());
            return accountFound.get();
        } else {
            log.warn("Account Not Found with id " + id);
            throw new AccountNotFoundException("Account not found with id " + id);
        }
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccounts(@RequestBody Accounts accounts) {
        // Users user=accounts.getUser();
        accountRepository.save(accounts);
        
    }

    @DeleteMapping("/accounts/{accountId}/{userId}")
    public void deleteAccount(@PathVariable("accountId") long id, @PathVariable("userId") int userId) {
        try {
            Optional<Users> userFound = userRepository.findById(userId);
            Optional<Accounts> accountFound = accountRepository.findById(id);
            Accounts account=accountFound.get();
            Users user=userFound.get();
           List<Accounts>accountList=user.getAccounts();
           accountList.remove(account);
           accountRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error deleting account with id " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping("/accounts/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItinerary(@RequestBody Accounts accounts) {
        accountRepository.save(accounts);
    }
}
