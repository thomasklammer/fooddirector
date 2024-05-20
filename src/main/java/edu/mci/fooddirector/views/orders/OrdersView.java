package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.model.services.UserService;
import edu.mci.fooddirector.util.DateTimeToStringConverter;
import edu.mci.fooddirector.util.OrderStatusToStringConverter;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;



@PageTitle("Meine Bestellungen | Fooddirector")
@Route(value = "orders", layout = MainLayout.class)
@PermitAll
public class OrdersView extends Div {
    public static int i;

    public OrdersView(OrderService orderService, UserService userService){

        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("custom-span");
        addClassName("padding-bottom");
        layout.setSpacing(false);

        var currentUser = userService.getCurrentUser();
        List<Order> orders = orderService.findAllByUserId(currentUser.get().getId());

        Grid<Order> grid = new Grid<>();
        grid.setItems(orders);
        grid.addColumn(Order::getId).setHeader("ID")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(order -> DateTimeToStringConverter.convert(order.getOrderDate())).setHeader("Bestelldatum")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(order -> {
            StringBuilder articleNames = new StringBuilder();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                articleNames.append(orderDetail.getArticle().getName()).append(", ");
            }
            if (!articleNames.isEmpty()) {
                articleNames.delete(articleNames.length() - 2, articleNames.length());
            }
            return articleNames.toString();
        })
                .setHeader("Artikel")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(x -> OrderStatusToStringConverter.convert(x.getOrderStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setAutoWidth(true);
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