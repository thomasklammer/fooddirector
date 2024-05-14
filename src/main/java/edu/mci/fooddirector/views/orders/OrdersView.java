package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;



@PageTitle("Bestellungen")
@Route(value = "orders", layout = MainLayout.class)

public class OrdersView extends Div {
    public static int i;

    public OrdersView(OrderService orderService){

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);

        List<Order> orders = orderService.findAll();

        Grid<Order> grid = new Grid<>();
        grid.setItems(orders);
        grid.addColumn(Order::getId).setHeader("ID");
        grid.addColumn(Order::getOrderDate).setHeader("Bestelldatum");
        grid.addColumn(Order::getOrderDetails).setHeader("Artikel");
        grid.addColumn(Order::getOrderStatus).setHeader("Status");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event -> {
            Order selectedOrder = event.getItem();
            UI.getCurrent().navigate("order-details/" + selectedOrder.getId());
        });

        layout.add(grid);

        layout.setWidth("auto");
        layout.setMargin(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);


        Div container = new Div();
        container.add(layout);

        setClassName("OrdersView");
        container.setClassName("OrdersViewContainer");

        add(container);
    }

}