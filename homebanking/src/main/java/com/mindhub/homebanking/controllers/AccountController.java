package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.repositories.AccountRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @GetMapping("/accounts")

    public List<AccountDTO> getAccounts(){

       return accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountByID(@PathVariable Long id, Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);

        if (account == null){
            return  new ResponseEntity<>("Cuenta no encontrada", HttpStatus.BAD_GATEWAY);
        }

        if (account.getOwner().equals(client)){
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity(accountDTO, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("Esta cuenta no es tuya", HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getCurrentAccounts(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());

        return client.getAccounts().stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toSet());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createNewAccount(Authentication authentication){

        Client clientNew = clientService.findByEmail((authentication.getName()));

        if(clientNew.getAccounts().size() >= 3){

            return new ResponseEntity<>("No se puede tener más de 3 cuentas",HttpStatus.FORBIDDEN);
        }
        else{
            Account account = new Account(createNum(), 0, LocalDate.now());
            clientNew.addAccount(account);
            accountService.accountSave(account);

            return new ResponseEntity<>(HttpStatus.CREATED);}

    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int createAccount() {
        int num;

        do {
            num = getRandomNumberUsingNextInt(00001000, 99999999);
        } while (accountService.existsByNumber("VIN"+num));
        return num;
    }

    public String createNum(){
        String number = "VIN"+createAccount();
        return number;
    }

}
