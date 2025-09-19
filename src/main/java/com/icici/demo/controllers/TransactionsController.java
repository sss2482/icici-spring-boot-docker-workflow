package com.icici.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.icici.demo.entities.Accounts;
import com.icici.demo.entities.Transactions;
import com.icici.demo.repos.TransactionsRepository;

import jakarta.transaction.Transactional;
import com.icici.demo.repos.UserRepository;   
import com.icici.demo.repos.AccountsRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
//import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



import com.icici.demo.entities.Users;
import com.icici.demo.controllers.AccountNotFoundException;
import com.icici.demo.dto.TransactionDTO;

@Slf4j
@RestController
@CrossOrigin
public class TransactionsController {

    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    AccountsRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/allTransactions")
    public List<Transactions> fetchAllTransactions() {
        // logic to fetch from DB
        List<Transactions> transactions = transactionsRepository.findAll().stream().toList();
        System.out.println(transactions);
        return transactions;
    }

    @GetMapping("/transactions")
    public List<TransactionDTO> fetchAllUserTransactions(@RequestHeader("userId") int userId) {
        // logic to fetch from DB
        Optional<Users> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            log.warn("User Not Found with id " + userId);
            throw new AccountNotFoundException("User not found with id " + userId);
        }
        List<Accounts> accounts = user.get().getAccounts();
        List<TransactionDTO> transactions = new ArrayList<>();
        accounts.stream().forEach(account -> {
            List<Transactions> sentTransactions = transactionsRepository.findBySenderAccount(account);
            List<Transactions> receivedTransactions = transactionsRepository.findByReceiverAccount(account);
            AtomicInteger id = new AtomicInteger(0);
            sentTransactions.stream().forEach(t -> {
                TransactionDTO tranDTO = new TransactionDTO();
                tranDTO.setId(id.getAndIncrement());
                tranDTO.setTransactionId(t.getId());
                tranDTO.setAmount(t.getAmount());
                tranDTO.setTime(t.getTime());
                tranDTO.setSenderAccountNumber(t.getSenderAccount().getAccountNumber());
                tranDTO.setReceiverAccountNumber(t.getReceiverAccount().getAccountNumber());
                tranDTO.setTransactionType("DEBIT");
                tranDTO.setAccountType(t.getSenderAccount().getAccountType());
                transactions.add(tranDTO);
            });

            
            receivedTransactions.stream().forEach(t -> {
                TransactionDTO tranDTO = new TransactionDTO();
                tranDTO.setId(id.getAndIncrement());
                tranDTO.setTransactionId(t.getId());
                tranDTO.setAmount(t.getAmount());
                tranDTO.setTime(t.getTime());
                tranDTO.setSenderAccountNumber(t.getSenderAccount().getAccountNumber());
                tranDTO.setReceiverAccountNumber(t.getReceiverAccount().getAccountNumber());
                tranDTO.setTransactionType("CREDIT");
                tranDTO.setAccountType(t.getReceiverAccount().getAccountType());
                transactions.add(tranDTO);
            });


            
        });
        
        return transactions;
    }

    @GetMapping("/transactions/{id}")
    public Transactions fetchATransaction(@PathVariable("id") int id) {
        Optional<Transactions> transactionsFound = transactionsRepository.findById(id);
        if (transactionsFound.isPresent()) {
            log.debug("Transaction Found " + transactionsFound.get());
            return transactionsFound.get();
        } else {
            log.warn("Transaction Not Found with id " + id);
            throw new TransactionNotFoundException("Trip not found with id " + id);
        }
    }

    @PostMapping("/transactions/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody Transactions transactions) {
        long senderAccountNumber = transactions.getSenderAccount().getAccountNumber();
        Optional<Accounts> senderAccount = accountRepository.findById(senderAccountNumber);
        if (!senderAccount.isPresent()) {
            log.warn("sender account not found");
            throw new AccountNotFoundException("sender account not found with id " + senderAccountNumber);
        }
        long receiverAccountNumber = transactions.getReceiverAccount().getAccountNumber();
        Optional<Accounts> receiverAccount = accountRepository.findById(receiverAccountNumber);
        if (!receiverAccount.isPresent()) {
            log.warn("receiver account not found");
            throw new AccountNotFoundException("receiver account not found with id " + receiverAccountNumber);
        }
        Accounts senderAccountMain = senderAccount.get();
        Accounts receiverAccountMain = receiverAccount.get();
        // System.out.println(senderAccount);
        int transactionAmount = transactions.getAmount();
        // System.out.println(transactionAmount);

        // System.out.println(receiverAccount);
        if (transactionAmount > senderAccountMain.getBalance()) {
            log.warn("sender does not have this much money");
            throw new InsufficientBalanceException("Transaction amount " + transactionAmount
                    + " is more than the available balance " + senderAccountMain.getBalance());

        }
        double senderFinalBalance = senderAccountMain.getBalance() - transactionAmount;
        double receiverFinalBalance = receiverAccountMain.getBalance() + transactionAmount;
        senderAccountMain.setBalance(senderFinalBalance);
        receiverAccountMain.setBalance(receiverFinalBalance);
        accountRepository.save(senderAccountMain);
        accountRepository.save(receiverAccountMain);
        transactions.setSenderAccount(senderAccountMain);
        transactions.setReceiverAccount(receiverAccountMain);
        transactionsRepository.save(transactions);

    }

}
