package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class CardUtilsTests {


    @Test
    public void cardNumberIsCreated(){

       String cardNumber = CardUtils.createCardNum();
        assertThat(cardNumber,is(not(emptyOrNullString())));

    }

    @Test
    public void cardNumberIsString(){

        String cardNumber = CardUtils.createCardNum();
        assertThat(cardNumber, isA(String.class));

    }

    @Test
    public void cardCvvIsNull(){

        int cardCvv= CardUtils.createCardCvv();

        assertThat(cardCvv, not(nullValue(Integer.class)));

    }

    @Test
    public void cardCvvHasThree(){
        int cardCvv= CardUtils.createCardCvv();
        String number = String.valueOf(cardCvv);

        assertThat(number, hasLength(3));
    }


}
