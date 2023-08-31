package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    private Client owner;

    private String number;
    private double balance;
    private LocalDate creationDate;

    @OneToMany(mappedBy = "transactionId", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    public Account (){

    }

    public Account(String number, double balance, LocalDate creationDate) {

        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public Client getOwner(){

        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setTransaction_id(this);
        transactions.add(transaction);
    }


}
