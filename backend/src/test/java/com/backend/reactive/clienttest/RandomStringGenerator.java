package com.backend.reactive.clienttest;

public class RandomStringGenerator {

    // defining a function to generate a random string of length l

    public static String RandGeneratedStr(int l) {

        // a list of characters to choose from in form of a string

        String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

        // creating a StringBuffer size of AlphaNumericStr

        StringBuilder s = new StringBuilder(l);

        int i;

        for (i = 0; i < l; i++) {

            //generating a random number using math.random()

            int ch = (int) (alphaNumericStr.length() * Math.random());

            //adding Random character one by one at the end of s

            s.append(alphaNumericStr.charAt(ch));

        }

        return s.toString();

    }
}
