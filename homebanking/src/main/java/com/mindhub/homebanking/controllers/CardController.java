package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;

import static com.mindhub.homebanking.models.CardType.CREDIT;
import static com.mindhub.homebanking.models.CardType.DEBIT;
import static com.mindhub.homebanking.utils.CardUtils.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private  CardService cardService;

    @GetMapping("/clients/current/cards")
    public CardDTO getCurrentCards(Authentication authentication){
        return cardService.getCurrentCardsDTO(authentication);
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(CardType cardType, CardColor cardColor, Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());
        String type = cardType.toString();

        if ((client.cardCount(type) < 3)) {

            Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, createCardNumberUnique(), createCardCvvUnique(), LocalDate.now(), LocalDate.now().plusYears(5));
            client.addCard(card);
            cardService.save(card);
            return new ResponseEntity<>("Tarjeta creada", HttpStatus.ACCEPTED);
        }else{
            return  new ResponseEntity<>("No se puede tener mas de 3 tarjetas de "+cardType, HttpStatus.FORBIDDEN);
        }

    }

    public int createCardCvvUnique() {
        int num;

        do {
            num = createCardCvv();
        } while (cardService.existsByCvv(num));
        return num;
    }


      public String createCardNumberUnique() {
            String number;
            do {
                number = createCardNum();
            } while (cardService.existsByNumber(number));

            return number;
      }





}
