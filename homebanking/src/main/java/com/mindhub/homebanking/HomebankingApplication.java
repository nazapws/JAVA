package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
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

		};

	}




}
