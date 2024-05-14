package edu.mci.fooddirector.views.cart;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.views.MainLayout;

@PageTitle("Warenkorb | Fooddirector")
@Route(value = "cart-success", layout = MainLayout.class)

public class CartSuccessView extends VerticalLayout {
    public CartSuccessView(CartService cartService) {
        cartService.clearCart();


        VerticalLayout layout = new VerticalLayout();

        H1 successTitle = new H1("Bestellung erfolgreich!");
        layout.add(successTitle);

        layout.add("Vielen Dank für Ihre Bestellung. Ihre Bestellung wurde erfolgreich aufgegeben.");


        Button homeButton = new Button("Zurück zur Startseite");
        homeButton.addClickListener(event -> {
            UI.getCurrent().navigate("");
        });
        layout.add(homeButton);

        add(layout);
    }


}
