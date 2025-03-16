package com.repo.api.util;

import java.util.Random;

public class NumberGenerator {

    private static String prefix;
    private static Long number=0L;

    public NumberGenerator(String prefix){
        NumberGenerator.prefix = prefix;
    }


    public static String generateNumber() {
        Random rand = new Random();
        long resRandom = rand.nextLong((20000000)+1)+10;
      return String.valueOf(2)+resRandom+number++;
    }

}
