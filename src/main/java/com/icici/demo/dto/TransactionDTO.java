package com.icici.demo.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TransactionDTO {
    int id;
    int transactionId;
    int amount;
    LocalDate time;
    long senderAccountNumber;
    long receiverAccountNumber;
    String transactionType;
    String accountType;


}
