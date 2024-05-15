package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@PageTitle("Admin Bestellungen | Fooddirector")
@Route(value = "Adminorders", layout = MainLayout.class)
@PermitAll
public class AdminOrdersView extends Div {

    private static final Logger LOGGER = Logger.getLogger(AdminOrdersView.class.getName());

    public AdminOrdersView(OrderService orderService) {
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("custom-span");
        addClassName("padding-bottom");
        layout.setSpacing(false);

        List<Order> orders = orderService.findAll();

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

        Grid.Column<Order> statusColumn = grid.addColumn(order -> order.getOrderStatus().toString()).setHeader("Status").setSortable(true);

        Binder<Order> binder = new Binder<>(Order.class);
        grid.getEditor().setBinder(binder);
        grid.getEditor().setBuffered(true);

        ComboBox<OrderStatus> comboBox = new ComboBox<>();
        comboBox.setItems(OrderStatus.values());
        binder.forField(comboBox).bind(Order::getOrderStatus, Order::setOrderStatus);
        statusColumn.setEditorComponent(comboBox);

        grid.addItemClickListener(event -> {
            grid.getEditor().editItem(event.getItem());
            comboBox.focus();
            Notification.show("change value");
        });

        grid.getEditor().addCloseListener(event -> {
            if (binder.hasChanges()) {
                Order order = event.getItem();
                try {
                    if (order.getOrderStatus() == null) {
                        Notification.show("Status cannot be null");
                    } else {
                        Notification.show("try to save");
                        orderService.saveOrder(order);
                        grid.getDataProvider().refreshItem(order);
                        Notification.show("Order successfully saved");
                    }
                } catch (Exception e) {
                    Notification.show("Internal error. Please notify the administrator.");
                }
            }
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
