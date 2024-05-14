package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;

@PageTitle("Bestelldetails")
@Route(value = "order-details/:id", layout = MainLayout.class)
public class OrderDetailsView extends Div implements BeforeEnterObserver {

    private final VerticalLayout layout;
    private final OrderService orderService;

    public OrderDetailsView(OrderService orderService) {
        layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setWidth("auto");
        layout.setMargin(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        add(layout);
        this.orderService = orderService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long orderId = event.getRouteParameters().get("id").map(Long::parseLong).orElse(null);
        if (orderId != null) {
            Order selectedOrder = orderService.findOrderById(orderId);
            if (selectedOrder != null) {
                layout.removeAll();
                layout.add(new H3("Bestellung #" + selectedOrder.getId()));
                layout.add(new Span("Bestelldatum: " + selectedOrder.getOrderDate()));
                layout.add(new Span("Lieferadresse: " + selectedOrder.getDeliveryAddress()));

                // Add order details
                for (OrderDetail orderDetail : selectedOrder.getOrderDetails()) {
                    layout.add(new Span(orderDetail.getAmount() + "x " + orderDetail.getArticleId() + " - " + orderDetail.getNote()));
                }

                addBackButton();
            } else {
                layout.add(new Span("Die Bestellung konnte nicht gefunden werden."));
            }
        } else {
            layout.add(new Span("Es wurde keine Bestellungs-ID angegeben."));
        }
    }

    private void addBackButton() {
        Button backButton = new Button("Zurück");
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(OrdersView.class)));
        layout.add(backButton);
    }
}