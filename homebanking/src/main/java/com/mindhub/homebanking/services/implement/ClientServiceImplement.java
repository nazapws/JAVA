package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDTO() {
        List<Client> allClients = clientRepository.findAll();

        List<ClientDTO> transformList = allClients
                .stream()
                .map(currentClient -> new ClientDTO(currentClient))
                .collect(Collectors.toList());

        return transformList;
    }

    @Override
    public ClientDTO getCurrentClientDTO(Authentication authentication){
        Client clientOptional = clientRepository.findByEmail((authentication.getName()));
        return  new ClientDTO(clientOptional);
    }

    @Override
    public Client findById(long id){
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ClientDTO getClientByIdDTO(long id){
        return new ClientDTO(this.findById(id));
    }

    @Override
    public Client findByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    @Override
    public void clientSave(Client client){
        clientRepository.save(client);
    }



}

