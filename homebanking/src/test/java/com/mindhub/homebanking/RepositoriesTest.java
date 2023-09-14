package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.mindhub.homebanking.models.CardColor.GOLD;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;
import static java.util.Arrays.asList;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

    @DataJpaTest
    @AutoConfigureTestDatabase(replace = NONE)

    public class RepositoriesTest {

        @Autowired
        CardRepository cardRepository;
        @Autowired
        ClientRepository clientRepository;
        @Autowired
        TransactionRepository transactionRepository;
        @Autowired
        AccountRepository accountRepository;
        @Autowired
        LoanRepository loanRepository;

        @Test
        public void existLoans() {
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, is(not(empty())));
        }

        @Test
        public void existPersonalLoan() {
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }

        @Test
        public void existMoreAccountsByClient() {

            List<Integer> amounts = clientRepository.findAll().stream().map(amount -> amount.getAccounts().size()).collect(Collectors.toList());
            assertThat(amounts, hasSize(lessThan(3)));

        }
        @Test
        public void existClientAdmin()
        {
            List<String> clientsEmail = clientRepository.findAll().stream().map(email -> email.getEmail()).collect(Collectors.toList());
            assertThat(clientsEmail, hasItem("melba@mindhub.com"));
      }

        @Test
        public void AccountHas()
        {
            List<String> account = accountRepository.findAll().stream().map(acc -> acc.getNumber()).collect(Collectors.toList());
            String account1 = account.get(1);
            assertThat(account1, startsWith("VIN"));
        }

        @Test
        public void AccountNotEmpty() {
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("balance")));
        }

        @Test
        public void TransactionHasDebit() {
            List<Transaction> type = transactionRepository.findAll();
            assertThat(type, hasItem(hasProperty("type", is(DEBIT))));
        }

        @Test
        public void TransactionDate() {

            List<Transaction> type = transactionRepository.findAll();
            assertThat(type, hasItem(hasProperty("date", isA(LocalDate.class))));

        }


        @Test
        public void cardColorGold(){

            List<Card> cards = cardRepository.findAll();
            assertThat(cards, hasItem(hasProperty("color", is(GOLD))));

        }

        @Test
        public void cardExist(){

            List<Card> cards = cardRepository.findAll();
            assertThat(cards, is(not(empty())));


        }






    }
