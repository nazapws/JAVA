package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private  AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {

        List<Client> allClients = clientRepository.findAll();

        List<ClientDTO> transformList = allClients
                .stream()
                .map(currentClient -> new ClientDTO(currentClient))
                .collect(Collectors.toList());

        return transformList;

    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        Client clientOptional = clientRepository.findByEmail((authentication.getName()));
        return  new ClientDTO(clientOptional);
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);

        return new ClientDTO(clientOptional.get());
    }




    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Por favor, llenar todos los datos", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Este email ya se encuentra registrado", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        Account account = new Account(createNum(), 0, LocalDate.now());
        client.addAccount(account);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int createAccount() {

        int numRan;

        do {
            numRan = getRandomNumberUsingNextInt(00000001, 99999999);
        } while (accountRepository.existsByNumber("VIN"+numRan));
        return numRan;
    }

    public String createNum() {
        String number = "VIN" + createAccount();
        return number;
    }

}





