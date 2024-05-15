package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.model.services.UserService;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;



@PageTitle("Bestellungen")
@Route(value = "orders", layout = MainLayout.class)
@PermitAll
public class OrdersView extends Div {
    public static int i;

    public OrdersView(OrderService orderService, UserService userService){

        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("custom-span");
        layout.setSpacing(false);

        var currentUser = userService.getCurrentUser();
        List<Order> orders = orderService.findAllByUserId(currentUser.get().getId());

        Grid<Order> grid = new Grid<>();
        grid.setItems(orders);
        grid.addColumn(Order::getId).setHeader("ID").setSortable(true);
        grid.addColumn(order -> order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setHeader("Bestelldatum").setSortable(true);
        grid.addColumn(order -> {
            StringBuilder articleNames = new StringBuilder();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                articleNames.append(orderDetail.getArticle().getName()).append(", ");
            }
            if (articleNames.length() > 0) {
                articleNames.delete(articleNames.length() - 2, articleNames.length());
            }
            return articleNames.toString();
        }).setHeader("Artikel").setSortable(true);
        grid.addColumn(Order::getOrderStatus).setHeader("Status").setSortable(true);
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