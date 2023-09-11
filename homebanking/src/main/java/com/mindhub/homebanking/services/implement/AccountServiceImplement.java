package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public  void accountSave(Account account){
        accountRepository.save(account);
    }

    @Override
    public boolean existsByNumber(String number){
        return accountRepository.existsByNumber(number);
    }

    @Override
    public List<AccountDTO> getAccountsDTO(){

        List<Account> allTrans = accountRepository.findAll();

        List<AccountDTO> newList = allTrans
                .stream()
                .map(currentList -> new AccountDTO(currentList))
                .collect(Collectors.toList());

        return newList;}

    @Override
    public Account findById(long id){
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

}
