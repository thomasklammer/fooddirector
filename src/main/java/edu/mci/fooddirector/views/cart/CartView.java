package edu.mci.fooddirector.views.cart;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.model.domain.*;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.model.services.NotificationService;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.model.services.UserService;
import edu.mci.fooddirector.util.DoubleToStringConverter;
import edu.mci.fooddirector.views.login.LoginView;
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
import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;
import java.util.ArrayList;

@PageTitle("Warenkorb | Fooddirector")
@Route(value = "cart", layout = MainLayout.class)
@PermitAll
public class CartView extends VerticalLayout {


    private Main content;
    private Cart cart = new Cart();

    private final CartService cartService;
    private final NotificationService notificationService;
    private final OrderService orderService;


    private UnorderedList summaryList;
    private Section cartItemsList;

    private Binder<Address> addressBinder;
    private String selectedPaymentMethod;

    private User currentUser;


    public CartView(CartService cartService,
                    NotificationService notificationService,
                    OrderService orderService,
                    UserService userService) {
        this.cartService = cartService;
        this.notificationService = notificationService;
        this.orderService = orderService;

        currentUser = userService.getCurrentUser().get();

        addClassNames("cart");
        addClassName("padding-bottom");

        content = new Main();
        content.addClassNames("cart-grid", Gap.XLARGE, AlignItems.START, JustifyContent.EVENLY, MaxWidth.FULL);

        H2 header = new H2("Warenkorb");


        add(header);
        add(content);

        cartService.getCart(cart -> {
            this.cart = cart;

            if (cart.isEmpty()) {
                VerticalLayout cartEmptyLayout = new VerticalLayout();
                cartEmptyLayout.add(new H3("Warenkorb ist leer!"));
                cartEmptyLayout.add("Leider ist der Warenkorb leer. Schauen Sie auf unsere Speisekarte um feinste Speisen zu entdecken");
                content.add(cartEmptyLayout);

            } else {
                cartItemsList = createItemsList();
                content.add(cartItemsList);
                content.add(createAside());
            }


        });

    }

    private void recreateCartItems() {
        var newCartItems = createItemsList();
        content.replace(cartItemsList, newCartItems);
        cartItemsList = newCartItems;
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

        for (var cartItem : cart.getCartItems()) {
            ul.add(createListItem(cartItem));
            ul.add(new Hr());
        }


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


        summaryList = new UnorderedList();
        summaryList.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.COLUMN, Gap.MEDIUM);

        updateSummary();

        summary.add(header, summaryList);
        return summary;
    }

    private void updateSummary() {
        summaryList.removeAll();

        summaryList.add(createSummaryListItem("Zwischensumme", DoubleToStringConverter.convertToCurrency(cart.getPrice()), false));
        summaryList.add(createSummaryListItem("Lieferung", DoubleToStringConverter.convertToCurrency(cart.getDeliveryCosts()), false));
        summaryList.add(createSummaryListItem("Gesamt (inkl. MwSt.)", DoubleToStringConverter.convertToCurrency(cart.getTotalPrice()), true));
    }

    private Section createPaymentMethods() {
        var paymentMethods = new Section();

        H3 header = new H3("Bezahlung");
        header.addClassNames(Margin.Bottom.MEDIUM, FontSize.XXLARGE);

        var paymentMethodsRadioGroup = new RadioButtonGroup<>();
        paymentMethodsRadioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        paymentMethodsRadioGroup.setItems("PayPal", "Kreditkarte", "BitCoin", "Bei Abholung");

        paymentMethodsRadioGroup.addValueChangeListener(event -> {
            var value = event.getValue();

            if (value != null) {
                selectedPaymentMethod = value.toString();
            } else {
                selectedPaymentMethod = null;
            }
        });

        paymentMethods.add(header, paymentMethodsRadioGroup);

        return paymentMethods;
    }

    private Section createShippingAddressSection() {
        Section shippingDetails = new Section();
        shippingDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);


        H3 header = new H3("Lieferadresse");
        header.addClassNames(Margin.Bottom.MEDIUM, FontSize.XXLARGE);


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


        addressBinder = new Binder<>(Address.class);
        addressBinder.setBean(currentUser.getDeliveryAddress());
        addressBinder.forField(postalCode).asRequired("Pflichtfeld").bind(Address::getZipCode, Address::setZipCode);
        addressBinder.forField(city).asRequired("Pflichtfeld").bind(Address::getCity, Address::setCity);
        addressBinder.forField(street).asRequired("Pflichtfeld").bind(Address::getStreet, Address::setStreet);
        addressBinder.forField(houseNumber).asRequired("Pflichtfeld").bind(Address::getHouseNumber, Address::setHouseNumber);


        subSection.add(postalCode, city);

        shippingDetails.add(header, streetSubSection, subSection);
        return shippingDetails;
    }


    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Vertical.MEDIUM);

        Button pay = new Button("Jetzt bestellen", new Icon(VaadinIcon.LOCK));
        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        pay.addClickListener(e -> {
            if (cart.isEmpty()) {
                notificationService.showWarning("Es befindet sich nichts im Warenkorb");
                return;
            }

            if (selectedPaymentMethod == null || selectedPaymentMethod.isEmpty()) {
                notificationService.showWarning("Bitte wählen Sie eine Bezahlmethode");
                return;
            }

            PaymentMethod paymentMethod;

            switch (selectedPaymentMethod) {
                case "Kreditkarte":
                    paymentMethod = PaymentMethod.CreditCard;
                    break;
                case "PayPal":
                    paymentMethod = PaymentMethod.PayPal;
                    break;
                case "BitCoin":
                    paymentMethod = PaymentMethod.BitCoin;
                    break;
                case "Bei Abholung":
                    paymentMethod = PaymentMethod.OnDelivery;
                    break;
                default:
                    notificationService.showWarning("Bitte wählen Sie eine gültige Bezahlmethode");
                    return;
            }


            addressBinder.validate();
            if (!addressBinder.isValid()) {
                return;
            }


            var order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.Confirmed);
            order.setPaymentMethod(paymentMethod);
            order.setUser(currentUser);

            var address = addressBinder.getBean();

            var deliveryAddress = new Address();
            deliveryAddress.setZipCode(address.getZipCode());
            deliveryAddress.setCity(address.getCity());
            deliveryAddress.setStreet(address.getStreet());
            deliveryAddress.setHouseNumber(address.getHouseNumber());

            order.setDeliveryAddress(deliveryAddress);

            var orderDetails = new ArrayList<OrderDetail>();
            for (var cartItem : cart.getCartItems()) {
                var orderDetail = new OrderDetail();
                orderDetail.setAmount(cartItem.getAmount());
                orderDetail.setArticle(cartItem.getArticle());
                orderDetail.setTaxRate(cartItem.getArticle().getTaxRate());
                orderDetail.setNetValue(cartItem.getArticle().getNetPriceDiscounted());
                orderDetail.setOrder(order);

                orderDetails.add(orderDetail);
            }
            order.setOrderDetails(orderDetails);


            try {
                orderService.saveOrder(order);
                UI.getCurrent().navigate(CartSuccessView.class);
            } catch (Exception ex) {
                notificationService.showError("Beim Bestellvorgang ist ein Fehler aufgetreten :(");
            }

        });

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
        if (bold) {
            headerspan.addClassNames(LumoUtility.FontWeight.BOLD);
        }

        Span priceSpan = new Span(price);

        if (bold) {
            priceSpan.addClassNames(LumoUtility.FontWeight.BOLD);
        }

        item.add(headerspan, priceSpan);
        return item;
    }

    private ListItem createListItem(CartItem cartItem) {
        ListItem item = new ListItem();
        item.addClassNames(Display.FLEX, FlexDirection.ROW, JustifyContent.BETWEEN);
        item.setMaxWidth("1000px");


        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexDirection.COLUMN);
        subSection.setWidth("400px");

        subSection.add(new Span(cartItem.getArticle().getName()));
        Span secondarySpan = new Span(cartItem.getArticle().getDescription());
        secondarySpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subSection.add(secondarySpan);


        var price = cartItem.getArticle().getGrossPriceDiscounted() * cartItem.getAmount();
        Span priceSpan = new Span(DoubleToStringConverter.convertToCurrency(price));
        priceSpan.addClassNames(Style.TextAlign.RIGHT.name());


        TextField amount = new TextField();
        amount.setWidth("50px");
        amount.setReadOnly(true);
        amount.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        amount.setValue(String.valueOf(cartItem.getAmount()));

        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.setAriaLabel("Menge verringern");

        Button minusButton = new Button(new Icon(VaadinIcon.MINUS));
        minusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        minusButton.setAriaLabel("Menge erhöhen");

        Button removeButton = new Button(new Icon(VaadinIcon.TRASH));
        removeButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        removeButton.setAriaLabel("Entfernen");


        plusButton.addClickListener(e -> {
            var newAmount = cartItem.getAmount() + 1;

            if (newAmount > 99) {
                notificationService.showWarning("Maximal 99 erlaubt");
                return;
            }

            cartItem.setAmount(newAmount);
            amount.setValue(String.valueOf(newAmount));
            cartService.setCart(cart);
            updateSummary();
            recreateCartItems();
        });

        minusButton.addClickListener(e -> {
            var newAmount = cartItem.getAmount() - 1;

            if (newAmount < 1) {
                notificationService.showWarning("Menge muss mindestens 1 sein");
                return;
            }

            cartItem.setAmount(newAmount);
            amount.setValue(String.valueOf(newAmount));
            cartService.setCart(cart);
            updateSummary();
            recreateCartItems();
        });

        removeButton.addClickListener(e -> {
            cart.removeCartItem(cartItem);
            cartService.setCart(cart);
            updateSummary();
            recreateCartItems();
        });


        var amountLayout = new HorizontalLayout();
        amountLayout.add(minusButton, amount, plusButton, removeButton);

        item.add(subSection, priceSpan, amountLayout);
        return item;
    }
}
