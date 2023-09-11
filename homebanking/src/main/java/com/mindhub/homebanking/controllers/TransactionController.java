package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransf(@RequestParam double amount, @RequestParam String description, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication) {
        if (authentication.getName() != null) {

            Client client = clientService.findByEmail(authentication.getName());
            Account accountOrigin = accountService.findByNumber(fromAccountNumber);
            Account accountDestiny = accountService.findByNumber(toAccountNumber);

            if (amount <= 0 || description.isBlank() || fromAccountNumber.isBlank() || toAccountNumber.isBlank()) {
                return new ResponseEntity<>("Se deben llenar todos los campos", HttpStatus.BAD_GATEWAY);

            } else {
                if (!toAccountNumber.equals(fromAccountNumber)) {

                    if ((accountService.existsByNumber(fromAccountNumber)) && (accountService.existsByNumber(toAccountNumber))) {


                        if (client.getAccountNumber(fromAccountNumber)!= null ) {

                            if (amount <= accountOrigin.getBalance()) {
                                Transaction transaction1 = new Transaction(TransactionType.DEBIT, LocalDate.now(), -amount, description+" "+fromAccountNumber);
                                Transaction transaction2 = new Transaction(TransactionType.CREDIT, LocalDate.now(), amount, description+" "+toAccountNumber);

                                accountOrigin.addTransaction(transaction1);
                                accountDestiny.addTransaction(transaction2);

                                transactionService.save(transaction1);
                                transactionService.save(transaction2);

                                double originBalance = accountOrigin.getBalance();
                                double destinyBalance = accountDestiny.getBalance();

                                accountOrigin.setBalance(originBalance - amount);
                                accountDestiny.setBalance(destinyBalance + amount);

                                accountService.accountSave(accountOrigin);
                                accountService.accountSave(accountDestiny);

                                return new ResponseEntity<>("Transferencia realizada con éxito", HttpStatus.ACCEPTED);
                            } else {
                                return new ResponseEntity<>("Balance insuficiente", HttpStatus.FORBIDDEN);
                            }

                        } else {

                            return new ResponseEntity<>("Solo puede transferir desde sus propias cuentas", HttpStatus.FORBIDDEN);

                        }
                    } else {
                        return new ResponseEntity<>("Por favor, verificar los numeros de cuenta destino", HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("No se puede transferir a la misma desde la misma cuenta hacia la misma cuenta", HttpStatus.FORBIDDEN);
                }
            }

        }else{
            return new ResponseEntity<>("Debe estar logueado para realizar una transferencia", HttpStatus.FORBIDDEN);
        }
    }
}



