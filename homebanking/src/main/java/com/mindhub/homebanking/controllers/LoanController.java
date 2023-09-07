package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;



    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {

        List<Loan> allTrans = loanRepository.findAll();

        List<LoanDTO> newList = allTrans
                .stream()
                .map(currentList -> new LoanDTO(currentList))
                .collect(Collectors.toList());

        return newList;

    }

    @Transactional
    @PostMapping("/loans")

    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        if(authentication.getName()!= null){
            Client client = clientRepository.findByEmail(authentication.getName());
            Account accountDestiny = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
            Loan loan = loanRepository.findLoanById(loanApplicationDTO.getLoanId());

                if(loanApplicationDTO.getAmount()>0 && loanApplicationDTO.getPayments() > 0){

                if(loanRepository.findLoanById(loanApplicationDTO.getLoanId())!=null){

                    if(loanApplicationDTO.getAmount() <= loanRepository.findLoanById(loanApplicationDTO.getLoanId()).getMaxAmount()){

                        if(loanRepository.findLoanById(loanApplicationDTO.getLoanId()).getPayments().contains(loanApplicationDTO.getPayments())){

                            if(accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())){

                                if(client.getAccountNumber(loanApplicationDTO.getToAccountNumber())!=null){



                                    ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()+(0.2*loanApplicationDTO.getAmount()), loanApplicationDTO.getPayments());
                                    clientLoanRepository.save(clientLoan);

                                    client.addClientLoan(clientLoan);
                                    loan.addClientLoan(clientLoan);

                                    Transaction transaction = new Transaction(TransactionType.CREDIT, LocalDate.now(), loanApplicationDTO.getAmount(), loan.getName()+" "+"loan approved");
                                    accountDestiny.addTransaction(transaction);
                                    transactionRepository.save(transaction);

                                    double balance = accountDestiny.getBalance();
                                    accountDestiny.setBalance(balance + loanApplicationDTO.getAmount());

                                    return new ResponseEntity<>("Préstamo generado", HttpStatus.ACCEPTED);
                                }else{
                                    return new ResponseEntity<>("El préstamo se debe acreditar a una cuenta propia", HttpStatus.FORBIDDEN);
                                }

                            }
                            else{
                                return new ResponseEntity<>("La cuenta destino debe existir", HttpStatus.FORBIDDEN);
                            }
                        }
                        else{
                            return new ResponseEntity<>("Debe seleccionar cantidad de cuotas disponibles según el crédito elegido", HttpStatus.FORBIDDEN);
                        }
                    }
                    else{
                        return new ResponseEntity<>("El monto solicitado, excede el monto disponible del crédito", HttpStatus.FORBIDDEN);
                    }

                }else{
                   return new ResponseEntity<>("Se debe seleccionar un préstamo existente", HttpStatus.FORBIDDEN);
                }

            }
            else{
                return new ResponseEntity<>("Ingresar cantidad de cuotas a pagar y monto del préstamo válido", HttpStatus.FORBIDDEN);
            }


        }
        else{
            return new ResponseEntity<>("Debe estar logueado", HttpStatus.FORBIDDEN);
        }

    }


}
