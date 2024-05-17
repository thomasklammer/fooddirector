package edu.mci.fooddirector.util;

import edu.mci.fooddirector.model.enums.OrderStatus;

public class OrderStatusToStringConverter {
    public static String convert(OrderStatus orderStatus) {
        switch (orderStatus) {
            case Confirmed -> {
                return "Bestätigt";
            }
            case Preparing -> {
                return "In Vorbereitung";
            }
            case OnDelivery -> {
                return "Wird geliefert";
            }
            case Delivered -> {
                return "Geliefert";
            }
        }

        return orderStatus.toString();
    }
}
