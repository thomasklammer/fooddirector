package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
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

import java.time.format.DateTimeFormatter;

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
        layout.setClassName("custom-span");
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
                addBackButton();
                layout.add(new H3("Bestellung Nr. " + selectedOrder.getId()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                layout.add(new Span("Bestelldatum: " + selectedOrder.getOrderDate().format(formatter)));
                layout.add(new Span("Lieferadresse: " + selectedOrder.getDeliveryAddress().getCity() + " " + selectedOrder.getDeliveryAddress().getStreet() + " " + selectedOrder.getDeliveryAddress().getHouseNumber()));
                layout.add(new Span("Bezahlmethode: " + selectedOrder.getPaymentMethod()));

                Grid<OrderDetail> grid = new Grid<>();
                grid.setItems(selectedOrder.getOrderDetails());
                grid.addColumn(orderDetail -> orderDetail.getArticleId().getName()).setHeader("Artikel").setSortable(true);
                grid.addColumn(OrderDetail::getAmount).setHeader("Menge").setSortable(true);
                grid.addColumn(orderDetail -> orderDetail.getTaxRate() + " %").setHeader("Tax Rate").setSortable(true);
                grid.addColumn(orderDetail -> "€ " + orderDetail.getNetValue()).setHeader("Net Value").setSortable(true);
                grid.addColumn(OrderDetail::getNote).setHeader("Anmerkung").setSortable(true);
                layout.add(grid);


            } else {
                layout.add(new Span("Die Bestellung konnte nicht gefunden werden."));
            }
        } else {
            layout.add(new Span("Es wurde keine Bestellungs-ID angegeben."));
        }
    }

    private void addBackButton() {
        Button backButton = new Button();
        backButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(OrdersView.class)));
        backButton.setClassName("custom-button");
        layout.add(backButton);
    }
}
