package com.icici.demo.dto;
import lombok.Data;

@Data
public class AccountInfoDTO {
    Long accountNumber;
    double balance;
    String accountType;
    String accountHolderName;
    String email;
    String phoneNumber;

}