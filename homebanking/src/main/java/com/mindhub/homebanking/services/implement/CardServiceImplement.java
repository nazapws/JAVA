package com.mindhub.homebanking.services.implement;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {


    @Autowired
    private CardRepository cardRepository;


    @Override
    public CardDTO getCurrentCardsDTO(Authentication authentication) {
        Card cards = cardRepository.findByCardHolder(authentication.getName());
        return new CardDTO(cards);
    }

    @Override
    public boolean existsByNumber(String number){
        return cardRepository.existsByNumber(number);
    }

    @Override
    public boolean existsByCvv(int cvv){

        return cardRepository.existsByCvv(cvv);
    }

    @Override
    public Card findByCardHolder(String cardHolder) {
        return cardRepository.findByCardHolder(cardHolder);
    }

    @Override
    public void save(Card card){
        cardRepository.save(card);
    }


}
