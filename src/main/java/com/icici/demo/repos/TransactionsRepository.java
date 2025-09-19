package com.icici.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icici.demo.entities.Accounts;
import com.icici.demo.entities.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
    List<Transactions> findBySenderAccount(Accounts account);
    List<Transactions> findByReceiverAccount(Accounts account);

}
