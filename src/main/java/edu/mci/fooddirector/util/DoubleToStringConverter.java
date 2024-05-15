package edu.mci.fooddirector.util;

import java.text.NumberFormat;
import java.util.Locale;

public  class DoubleToStringConverter {
    public static String convertToCurrency(double value) {
        var formattedNumber = convert(value);
        return formattedNumber + " €";
    }

    public static String convertToPercentage(double value) {
        var formattedNumber = convert(value);
        return formattedNumber + " %";
    }

    public static String convert(double value) {
        Locale locale = Locale.getDefault();
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        return numberFormat.format(value);
    }
}
