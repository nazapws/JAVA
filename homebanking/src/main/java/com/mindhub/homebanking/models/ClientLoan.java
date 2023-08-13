package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private int amount;


    private int payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="clientLoan_Id")
    private Client clientLoanId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_Id")
    private Loan loanId;

    public ClientLoan(){

    }
    public ClientLoan(int amount, int payments, Loan loanId, Client clientLoanId) {
        this.amount = amount;
        this.payments = payments;
        this.loanId = loanId;
        this.clientLoanId = clientLoanId;

    }

    public Long getId() {
        return id;
    }

   public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClientLoan_Id() {
        return clientLoanId;
    }

    public void setClientLoan_Id(Client clientLoanId) {
        this.clientLoanId = clientLoanId;
    }

    public Loan getLoan_Id() {
        return loanId;
    }

    public void setLoan_Id(Loan loanId) {

        this.loanId = loanId;
    }
}
