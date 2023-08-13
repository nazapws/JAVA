package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {
@Id
@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
@GenericGenerator(name="native", strategy = "native")
    private Long id;

    @OneToMany(mappedBy = "loanId", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    private String name;
    private int maxAmount;

    @ElementCollection
    @Column (name= "payment")
    private List<Integer> payments = new ArrayList<>();

    public Loan (){

    }


    public Loan(String name, int maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayment() {
        return payments;
    }

    public void setPayment(List<Integer> payment) {
        this.payments = payment;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan_Id(this);
        clientLoans.add(clientLoan);
    }

    public List<Client> getClients(){
        return clientLoans.stream()
                .map(clientl -> clientl.getClientLoan_Id()).collect(toList());
    }
}
