package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private Long id;

    private Long idloan;
    private String name;
    private int amount;
    private int payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        idloan = clientLoan.getLoan_Id().getId();
        name = clientLoan.getLoan_Id().getName();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getIdloan() {
        return idloan;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
