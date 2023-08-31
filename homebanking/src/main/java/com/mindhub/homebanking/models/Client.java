package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    private  String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "clientCards", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();
    public  Client (){

    };

    @OneToMany(mappedBy = "clientLoanId", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

    public String toString(){
        return firstName + " " + lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClientLoan_Id(this);
        clientLoans.add(clientLoan);
    }

    public Set<Card> getCards() {
        return cards;

    }

    public void addCard(Card card) {
        card.setClient_cards(this);
        cards.add(card);
    }

    public List<Loan> getLoans(){
        return clientLoans.stream()
                .map(cloan -> cloan.getLoan_Id()).collect(toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long cardCount(String cardType) {

       return cards.stream()
               .filter(card -> card.getType().name().equals(cardType)).count();
    }

    public List<Account> getAccountNumber(String account){
        return accounts.stream().filter(a -> a.getNumber().equals(account)).collect(toList());
    }




}
