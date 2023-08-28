package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;

    private String number;
    private double balance;

    private Set<TransactionDTO> transactions = new HashSet<>();
    private LocalDate creationDate;

    public AccountDTO(Account account) {
        id = account.getId();
        number = account.getNumber();
        balance = account.getBalance();
        creationDate = account.getCreationDate();
        transactions = account.getTransactions()
                .stream()
                .map(currentTransaction -> new TransactionDTO(currentTransaction))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getDate() {
        return creationDate;
    }

    public  Set<TransactionDTO> getTransactions(){
        return transactions;
    }
}
