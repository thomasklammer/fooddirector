package edu.mci.fooddirector.views.cart;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.model.callbacks.CartCallback;
import edu.mci.fooddirector.model.domain.Cart;
import edu.mci.fooddirector.model.domain.CartItem;
import edu.mci.fooddirector.model.enums.ArticleCategory;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexWrap;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Position;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

@PageTitle("Warenkorb | Fooddirector")
@Route(value = "cart", layout = MainLayout.class)
public class CartView extends VerticalLayout {


    public CartView(CartService cartService) {


        cartService.getCart(new CartCallback() {
            @Override
            public void onCartLoaded(Cart cart) {
                System.out.println("test");

            }
        });

        addClassNames("cart");


        H2 header = new H2("Warenkorb");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.NONE, FontSize.XXXLARGE, Margin.Bottom.XLARGE);

        var content = new Main();
        content.addClassNames("cart-grid", Gap.XLARGE, AlignItems.START, JustifyContent.EVENLY, MaxWidth.FULL);
        //content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.EVENLY, MaxWidth.FULL);

        content.add(createItemsList());
        content.add(createAside());

        add(header);
        add(content);
    }





    private Section createItemsList() {
        Section itemsList = new Section();
        Header headerSection = new Header();
        headerSection.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Bottom.MEDIUM);
        H3 header = new H3("Bestellung");
        header.addClassNames(Margin.NONE);

        headerSection.add(header);

        UnorderedList ul = new UnorderedList();
        ul.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.COLUMN, Gap.MEDIUM);

        ul.add(createListItem("Vanilla cracker", "With wholemeal flour", "$7.00"));
        ul.add(createListItem("Vanilla blueberry cake", "With blueberry jam", "$8.00"));
        ul.add(createListItem("Vanilla pastry", "With wholemeal flour", "$5.00"));

        itemsList.add(headerSection, ul);

        return itemsList;
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);

        checkoutForm.add(createSummary());
        checkoutForm.add(createPaymentMethods());
        checkoutForm.add(createShippingAddressSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createSummary() {
        var summary = new Section();
        summary.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        H3 header = new H3("Zusammenfassung");
        header.addClassNames(Margin.Bottom.MEDIUM, FontSize.XXLARGE);


        UnorderedList ul = new UnorderedList();
        ul.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.COLUMN, Gap.MEDIUM);

        ul.add(createSummaryListItem("Zwischensumme", "25,90 €", false));
        ul.add(createSummaryListItem("Lieferung",  "3,00 €", false));
        ul.add(createSummaryListItem("Gesamt (inkl. MwSt.)",  "28,90 €", true));


        summary.add(header, ul);
        return summary;
    }

    private Section createPaymentMethods() {
        var paymentMethods = new Section();

        H3 header = new H3("Bezahlung");
        header.addClassNames(Margin.Bottom.MEDIUM, FontSize.XXLARGE);

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setItems("PayPal", "Kreditkarte", "BitCoin", "Bei Abholung");

        paymentMethods.add(header, radioGroup);

        return paymentMethods;
    }

    private Section createShippingAddressSection() {
        Section shippingDetails = new Section();
        shippingDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);


        H3 header = new H3("Lieferadresse");
        header.addClassNames(Margin.Bottom.MEDIUM, FontSize.XXLARGE);


        //name
        Div nameSubSection = new Div();
        nameSubSection.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        TextField firstName = new TextField("Vorname");
        firstName.setRequiredIndicatorVisible(true);
        firstName.addClassNames(Margin.Bottom.SMALL);

        TextField lastName = new TextField("Nachname");
        lastName.setRequiredIndicatorVisible(true);
        lastName.addClassNames(Flex.GROW, Margin.Bottom.SMALL);
        nameSubSection.add(firstName, lastName);


        //street, housenumber
        Div streetSubSection = new Div();
        streetSubSection.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        TextField street = new TextField("Straße");
        street.setRequiredIndicatorVisible(true);
        street.addClassNames(Margin.Bottom.SMALL);

        TextField houseNumber = new TextField("Hausnummer");
        houseNumber.setRequiredIndicatorVisible(true);
        houseNumber.addClassNames(Flex.GROW, Margin.Bottom.SMALL);

        streetSubSection.add(street, houseNumber);

        //zipcode, city
        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        TextField postalCode = new TextField("Postleitzahl");
        postalCode.setRequiredIndicatorVisible(true);
        postalCode.addClassNames(Margin.Bottom.SMALL);

        TextField city = new TextField("Stadt");
        city.setRequiredIndicatorVisible(true);
        city.addClassNames(Flex.GROW, Margin.Bottom.SMALL);

        subSection.add(postalCode, city);



        shippingDetails.add(header, nameSubSection, streetSubSection, subSection);
        return shippingDetails;
    }


    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Vertical.MEDIUM);


        Button pay = new Button("Jetzt bestellen", new Icon(VaadinIcon.LOCK));
        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        footer.add(pay);
        return footer;
    }





    private Aside createAside() {
        var aside = new Aside();
        aside.addClassNames(Background.CONTRAST_5, BoxSizing.BORDER, Padding.LARGE, BorderRadius.LARGE,
                Position.STICKY);


        aside.add(createCheckoutForm());
        return aside;
    }

    private ListItem createSummaryListItem(String header, String price, boolean bold) {
        ListItem item = new ListItem();
        item.addClassNames(Display.FLEX, JustifyContent.BETWEEN);

        Span headerspan = new Span(header);
        if(bold) {
            headerspan.addClassNames(LumoUtility.FontWeight.BOLD);
        }

        Span priceSpan = new Span(price);

        if(bold) {
            priceSpan.addClassNames(LumoUtility.FontWeight.BOLD);
        }

        item.add(headerspan, priceSpan);
        return item;
    }

    private ListItem createListItem(String primary, String secondary, String price) {
        ListItem item = new ListItem();
        item.addClassNames(Display.FLEX, JustifyContent.BETWEEN);

        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexDirection.COLUMN);

        subSection.add(new Span(primary));
        Span secondarySpan = new Span(secondary);
        secondarySpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subSection.add(secondarySpan);

        Span priceSpan = new Span(price);

        item.add(subSection, priceSpan);
        return item;
    }
}
