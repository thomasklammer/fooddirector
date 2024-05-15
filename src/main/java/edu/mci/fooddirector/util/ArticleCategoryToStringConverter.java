package edu.mci.fooddirector.util;

import edu.mci.fooddirector.model.enums.ArticleCategory;

public class ArticleCategoryToStringConverter {
    public static String convert(ArticleCategory category) {
        switch (category) {
            case Starter -> {
                return "Vorspeisen";
            }
            case MainDish -> {
                return "Hauptspeisen";
            }
            case Dessert -> {
                return "Desserts";
            }
            case SideDish -> {
                return "Sides";
            }
            case Beverage -> {
                return "Getränke";
            }
        }

        return category.toString();
    }
}
