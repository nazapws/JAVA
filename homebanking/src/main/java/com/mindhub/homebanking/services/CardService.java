package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.security.core.Authentication;

public interface CardService {

    CardDTO getCurrentCardsDTO(Authentication authentication);
    boolean existsByNumber(String number);

    boolean existsByCvv(int cvv);

    Card findByCardHolder(String cardHolder);

    void save(Card card);



}
