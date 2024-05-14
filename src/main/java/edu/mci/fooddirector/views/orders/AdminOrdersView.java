package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;

import java.time.format.DateTimeFormatter;
import java.util.List;


@PageTitle("Admin Bestellungen")
@Route(value = "Adminorders", layout = MainLayout.class)

public class AdminOrdersView extends Div {
    public static int i;

    public AdminOrdersView(OrderService orderService){

        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("custom-span");
        layout.setSpacing(false);

        List<Order> orders = orderService.findAll();

        Grid<Order> grid = new Grid<>();
        grid.setItems(orders);
        grid.addColumn(Order::getId).setHeader("ID").setSortable(true);
        grid.addColumn(order -> order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setHeader("Bestelldatum").setSortable(true);
        grid.addColumn(order -> {
            StringBuilder articleNames = new StringBuilder();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                articleNames.append(orderDetail.getArticleId().getName()).append(", ");
            }
            if (articleNames.length() > 0) {
                articleNames.delete(articleNames.length() - 2, articleNames.length());
            }
            return articleNames.toString();
        }).setHeader("Artikel").setSortable(true);

        grid.addColumn(order -> order.getOrderStatus().toString())
                .setHeader("Status")
                .setSortable(true)
                .setEditorComponent(order -> {
                    ComboBox<OrderStatus> comboBox = new ComboBox<>();
                    comboBox.setItems(OrderStatus.values());
                    comboBox.setValue(order.getOrderStatus());
                    comboBox.addValueChangeListener(event -> {
                        order.setOrderStatus(event.getValue());
                        orderService.saveOrder(order);
                        grid.getDataProvider().refreshAll();
                    });
                    return comboBox;
                });

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

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