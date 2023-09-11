package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    void accountSave(Account account);

    boolean existsByNumber(String number);

    List<AccountDTO> getAccountsDTO();

    Account findById(long id);

    Account findByNumber(String number);
}
