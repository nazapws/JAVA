package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils(){

    }

    public static String createCardNum() {
        String num;

        num = getRandomNumberUsingNextInt(1000, 9999)+"-"
                + getRandomNumberUsingNextInt(1000, 9999)+"-"
                +getRandomNumberUsingNextInt(1000, 9999)+"-"
                +getRandomNumberUsingNextInt(1000, 9999);


        return num;
    }

    public static int createCardCvv() {
        int num;
        return num= getRandomNumberUsingNextInt(100, 999);
    }
    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}
