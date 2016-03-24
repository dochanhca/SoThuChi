package com.example.anhlamrduc.sothuchi.utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by AnhlaMrDuc on 21-Mar-16.
 */
public class Currency {

    public static String getCurrency(double number) {
        NumberFormat formatter = new DecimalFormat("#,###,###");
        String formattedNumber = formatter.format(number);
        return formattedNumber;
    }
}
