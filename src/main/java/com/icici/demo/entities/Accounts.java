package com.icici.demo.entities;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.icici.demo.entities.Accounts;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long accountNumber;
    double balance;
    String accountType;
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "customerId", nullable = false)
    // Users user;

}