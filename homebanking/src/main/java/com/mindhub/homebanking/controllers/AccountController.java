package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mindhub.homebanking.repositories.AccountRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")

    public List<AccountDTO> getAccounts(){

    List<Account> allTrans = accountRepository.findAll();

        List<AccountDTO> newList = allTrans
                .stream()
                .map(currentList -> new AccountDTO(currentList))
                .collect(Collectors.toList());

        return newList;}

    @GetMapping("accounts/{id}")
    public AccountDTO getAccountByID(@PathVariable Long id){
        Optional<Account> accountOptional = accountRepository.findById(id);

        return new AccountDTO(accountOptional.get());

    }

}
