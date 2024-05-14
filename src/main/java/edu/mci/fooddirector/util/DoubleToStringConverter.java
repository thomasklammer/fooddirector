package edu.mci.fooddirector.util;

import java.text.NumberFormat;
import java.util.Locale;

public  class DoubleToStringConverter {
    public static String ConvertToCurrency(double value) {
        Locale locale = Locale.getDefault();
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        String formattedNumber = numberFormat.format(value);

        return formattedNumber + " €";
    }
}
