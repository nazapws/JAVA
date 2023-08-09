package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
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

		};

	}




}
