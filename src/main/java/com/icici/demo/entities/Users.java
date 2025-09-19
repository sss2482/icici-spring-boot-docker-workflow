package com.icici.demo.entities;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import com.icici.demo.entities.Accounts;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int customerId;
    String name;
    int balance;
    String email;
    String password;
    String phoneNumber;
    @OneToMany (cascade =CascadeType.ALL)
    List <Accounts> accounts=new ArrayList<>();
            

    // public int getId() {
    //     return id;
    // }

    // public void setId(int id) {
    //     this.id = id;
    // }

    // public String getName() {
    //     return Name;
    // }

    // public void setName(String name) {
    //     Name = name;
    // }

    // public Long getAccountNumber() {
    //     return AccountNumber;
    // }

    // public void setAccountNumber(Long accountNumber) {
    //     AccountNumber = accountNumber;
    // }

    // public int getBalance() {
    //     return Balance;
    // }

    // public void setBalance(int balance) {
    //     Balance = balance;
    // }

}