package com.example.anhlamrduc.sothuchi.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by AnhlaMrDuc on 01-May-16.
 */
public class Constant {
    public static Locale current = Locale.ENGLISH;
    public static final String SHARED_PREFRENCES = "SharedPreferences";
    public static SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm", current);
    public static  SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", current);
    public static  SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM/yyyy", current);
    public static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", current);
    public static SimpleDateFormat VN_DATE_FORMAT = new SimpleDateFormat("HH:mm dd/MM/yyyy", current);
    public static SimpleDateFormat GMT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", current);
    public static String VND_FORMAT = "###,###,###";
    public static final int MAX_EDT_MONEY_NUMBER = 12;
}
