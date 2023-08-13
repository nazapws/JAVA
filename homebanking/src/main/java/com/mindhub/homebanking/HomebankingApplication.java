package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientLoanRepository clientLoanRepository, LoanRepository loanRepository, ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client1 = new Client("Nazareth", "Guia", "nazguiag@gmail.com");

			clientRepository.save(client);
			clientRepository.save(client1);



			Account account = new Account("VIN001", 5000, LocalDate.now());
			Account account1 = new Account("VIN002", 7500,LocalDate.now().plusDays(1));



			client.addAccount(account);
			client.addAccount(account1);

			accountRepository.save(account);
			accountRepository.save(account1);

			Transaction trans = new Transaction(CREDIT, LocalDate.now(), 8000, "Mercado Pago");
			Transaction trans1 = new Transaction(CREDIT, LocalDate.now(), 6000, "Mercado Pago");
			Transaction trans2 = new Transaction(DEBIT, LocalDate.now(), -3000, "Personal Fibertel");
			Transaction trans3 = new Transaction(CREDIT, LocalDate.now(), 1000, "Netflix");
			Transaction trans4 = new Transaction(DEBIT, LocalDate.now(), -1500, "Netflix");


			account.addTransaction(trans);
			account.addTransaction(trans1);
			account.addTransaction(trans4);

			account1.addTransaction(trans2);
			account1.addTransaction(trans3);

			transactionRepository.save(trans);
			transactionRepository.save(trans1);
			transactionRepository.save(trans4);
			transactionRepository.save(trans2);
			transactionRepository.save(trans3);

			Loan loanHipotecario = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60));
			Loan loanPersonal = new Loan("Personal",100000, List.of(6,12,24));
			Loan loanAutomotriz = new Loan("Automotriz", 300000, List.of(6,12,24,36));

			loanRepository.save(loanPersonal);
			loanRepository.save(loanHipotecario);
			loanRepository.save(loanAutomotriz);


			ClientLoan loanMelba = new ClientLoan(400000, 60, loanHipotecario, client);
			ClientLoan loanMelba1 = new ClientLoan(50000, 12, loanPersonal, client);
			ClientLoan loanNaza = new ClientLoan(100000, 24, loanPersonal, client1);
			ClientLoan loanNaza1 = new ClientLoan(200000, 36, loanAutomotriz, client1);

			clientLoanRepository.save(loanMelba);
			clientLoanRepository.save(loanMelba1);
			clientLoanRepository.save(loanNaza);
			clientLoanRepository.save(loanNaza1);
		};

	}




}
