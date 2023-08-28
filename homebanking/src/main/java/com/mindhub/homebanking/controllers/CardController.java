package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;

import static com.mindhub.homebanking.models.CardType.CREDIT;
import static com.mindhub.homebanking.models.CardType.DEBIT;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients/current/cards")
    public CardDTO getCurrentCards(Authentication authentication){
        Card cards = cardRepository.findByCardHolder(authentication.getName());
        return new CardDTO(cards);
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(CardType cardType, CardColor cardColor, Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        String type = cardType.toString();

        if ((client.cardCount(type) < 3)) {

            Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, createNum(), createCardCvv(), LocalDate.now(), LocalDate.now().plusYears(5));
            client.addCard(card);
            cardRepository.save(card);
            return new ResponseEntity<>("Tarjeta creada", HttpStatus.ACCEPTED);
        }else{
            return  new ResponseEntity<>("No se puede tener mas de 3 tarjetas de "+cardType, HttpStatus.FORBIDDEN);
        }

    }

    public int createCardCvv() {
        int num;

        do {
            num = getRandomNumberUsingNextInt(100, 999);
        } while (cardRepository.existsByCvv(num));
        return num;
    }



    public int createCardNum() {
        int num;

        do {
            num = getRandomNumberUsingNextInt(1000, 9999);
        } while (cardRepository.existsByNumber(num+"-"+num+"-"+num+"-"+num));
        return num;
    }

    public String createNum() {
        String number = createCardNum()+"-"+createCardNum()+"-"+createCardNum()+"-"+createCardNum();
        return number;
    }
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
