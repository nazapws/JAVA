package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientsDTO();

    ClientDTO getCurrentClientDTO(Authentication authentication);

    Client findById( long id);

    ClientDTO getClientByIdDTO(long id);

    Client findByEmail(String email);

    void clientSave(Client client);



}
