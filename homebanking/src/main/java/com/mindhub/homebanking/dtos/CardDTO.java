package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {

    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate dateFrom;
    private LocalDate thruDate;

    public CardDTO(Card card) {
        id = card.getId();
        cardHolder = card.getCardHolder();
        type = card.getType();
        color = card.getColor();
        number = card.getNumber();
        cvv = card.getCvv();
        dateFrom = card.getFromDate();
        thruDate = card.getThruDate();

    }

    public String getCardHolder() {
        return cardHolder;
    }
    public Long getId() {
        return id;
    }



    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
     public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }


}
