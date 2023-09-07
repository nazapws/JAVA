package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEnconder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(CardRepository cardRepository, ClientLoanRepository clientLoanRepository, LoanRepository loanRepository, ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
			Client client = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("M1"));
			Client client1 = new Client("Nazareth", "Guia", "nazguiag@gmail.com", passwordEnconder.encode("N2"));

			clientRepository.save(client);
			clientRepository.save(client1);



			Account account = new Account("VIN001", 5000, LocalDate.now());
			Account account1 = new Account("VIN002", 7500,LocalDate.now().plusDays(1));
			Account account2 = new Account("VIN003", 10000, LocalDate.now());


			client.addAccount(account);
			client.addAccount(account1);
			client1.addAccount(account2);

			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);

			Transaction trans = new Transaction(CREDIT, LocalDate.now(), 8000, "Mercado Pago");
			Transaction trans1 = new Transaction(CREDIT, LocalDate.now(), 6000, "Mercado Pago");
			Transaction trans2 = new Transaction(DEBIT, LocalDate.now(), -3000, "Personal Fibertel");
			Transaction trans3 = new Transaction(CREDIT, LocalDate.now(), 1000, "Netflix");
			Transaction trans4 = new Transaction(DEBIT, LocalDate.now(), -1500, "Netflix");
			Transaction trans5 = new Transaction(CREDIT, LocalDate.now(), 1000, "Festival");
			Transaction trans6 = new Transaction(DEBIT, LocalDate.now(), -1500, "Netflix");

			account.addTransaction(trans);
			account.addTransaction(trans1);
			account.addTransaction(trans4);

			account1.addTransaction(trans2);
			account1.addTransaction(trans3);

			account2.addTransaction(trans5);
			account2.addTransaction(trans6);

			transactionRepository.save(trans);
			transactionRepository.save(trans1);
			transactionRepository.save(trans4);
			transactionRepository.save(trans2);
			transactionRepository.save(trans3);
			transactionRepository.save(trans5);
			transactionRepository.save(trans6);

			Loan loanHipotecario = new Loan("Hipotecario", 500000.00, List.of(12,24,36,48,60));
			Loan loanPersonal = new Loan("Personal",100000.00, List.of(6,12,24));
			Loan loanAutomotriz = new Loan("Automotriz", 300000.00, List.of(6,12,24,36));

			loanRepository.save(loanPersonal);
			loanRepository.save(loanHipotecario);
			loanRepository.save(loanAutomotriz);


			ClientLoan loanMelba = new ClientLoan(400000, 60);
			ClientLoan loanMelba1 = new ClientLoan(50000, 12);
			ClientLoan loanNaza = new ClientLoan(100000, 24);
			ClientLoan loanNaza1 = new ClientLoan(200000, 36);

			client.addClientLoan(loanMelba);
			client.addClientLoan(loanMelba1);
			loanAutomotriz.addClientLoan(loanMelba);
			loanHipotecario.addClientLoan(loanMelba1);
			client1.addClientLoan(loanNaza);
			client1.addClientLoan(loanNaza1);
			loanPersonal.addClientLoan(loanNaza);

			clientLoanRepository.save(loanMelba);
			clientLoanRepository.save(loanMelba1);
			clientLoanRepository.save(loanNaza);
			clientLoanRepository.save(loanNaza1);

			Card cardMelba1 = new Card(client.getFirstName()+" "+client.getLastName(), CardType.DEBIT, GOLD,"1234-1234-1234-1234", 234, LocalDate.now(), LocalDate.now().plusYears(5));
			Card cardMelba2 = new Card(client.getFirstName()+" "+client.getLastName(), CardType.CREDIT, TITANIUM,"1234-1234-1234-1235", 235, LocalDate.now(), LocalDate.now().plusYears(5));
			Card cardNaza1 = new Card(client1.getFirstName()+" "+client1.getLastName(), CardType.CREDIT,  SILVER,"1222-2222-2222-2222", 342, LocalDate.now(), LocalDate.now().plusYears(5));

			client.addCard(cardMelba1);
			client.addCard(cardMelba2);
			client1.addCard(cardNaza1);

			cardRepository.save(cardMelba1);
			cardRepository.save(cardMelba2);
			cardRepository.save(cardNaza1);

		};

	}




}
