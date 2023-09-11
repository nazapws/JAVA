package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getLoansDTO() {
        List<Loan> allTrans = loanRepository.findAll();

        List<LoanDTO> newList = allTrans
                .stream()
                .map(currentList -> new LoanDTO(currentList))
                .collect(Collectors.toList());

        return newList;
    }


    public Loan findLoanById(Long id){
        return  loanRepository.findLoanById(id);
    }

}
