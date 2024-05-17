package edu.mci.fooddirector.util;

import edu.mci.fooddirector.model.enums.PaymentMethod;

public class PaymentMethodToStringConverter {
    public static String convert(PaymentMethod paymentMethod) {
        switch (paymentMethod) {
            case CreditCard -> {
                return "Kreditkarte";
            }
            case BitCoin -> {
                return "Bitcoin";
            }
            case OnDelivery -> {
                return "Bei Abholung";
            }
            case PayPal -> {
                return "PayPal";
            }
        }

        return paymentMethod.toString();
    }
}
