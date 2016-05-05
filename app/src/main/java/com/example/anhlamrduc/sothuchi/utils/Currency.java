package com.example.anhlamrduc.sothuchi.utils;

import java.text.DecimalFormat;

/**
 * Created by AnhlaMrDuc on 21-Mar-16.
 */
public class Currency {


    public static String getCurrency(double number) {
        DecimalFormat nuDecimalFormat = new DecimalFormat(Constant.VND_FORMAT);
        String numberFormatted = nuDecimalFormat.format(number);
        return numberFormatted;
    }
}
