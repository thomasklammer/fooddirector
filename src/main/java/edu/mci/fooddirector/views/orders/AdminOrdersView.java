package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.services.NotificationService;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.util.DateTimeToStringConverter;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PageTitle("Admin Bestellungen | Fooddirector")
@Route(value = "Adminorders", layout = MainLayout.class)
@PermitAll
public class AdminOrdersView extends Div {


    private final OrderService orderService;
    private final Grid<Order> grid;
    private final NotificationService notificationService;

    public AdminOrdersView(OrderService orderService, NotificationService notificationService) {
        this.notificationService = notificationService;
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("custom-span");
        addClassName("padding-bottom");
        layout.setSpacing(false);

        List<Order> orders = orderService.findAll();

        grid = new Grid<>();
        grid.setItems(orders);
        grid.addColumn(Order::getId).setHeader("ID").setSortable(true);
        grid.addColumn(order -> DateTimeToStringConverter.convert(order.getOrderDate())).setHeader("Bestelldatum").setSortable(true);
        grid.addColumn(order -> {
            StringBuilder articleNames = new StringBuilder();
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                articleNames.append(orderDetail.getArticle().getName()).append(", ");
            }
            if (!articleNames.isEmpty()) {
                articleNames.delete(articleNames.length() - 2, articleNames.length());
            }
            return articleNames.toString();
        }).setHeader("Artikel").setSortable(true);

        grid.addColumn(order -> order.getOrderStatus().toString()).setHeader("Status").setSortable(true);
        grid.addComponentColumn(this::createEditButton).setHeader("Aktionen");


        layout.add(grid);
        layout.setWidth("auto");
        layout.setMargin(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Div container = new Div();
        container.add(layout);

        setClassName("OrdersView");
        container.setClassName("OrdersViewContainer");

        add(container);
        this.orderService = orderService;
    }

    private Button createEditButton(Order order) {
        Button editButton = new Button("Status bearbeiten", event -> openEditDialog(order));
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return editButton;
    }

    private void openEditDialog(Order order) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();


        ComboBox<OrderStatus> orderStatus = new ComboBox<>("Status", OrderStatus.values());
        orderStatus.setValue(order.getOrderStatus());

        Button saveButton = new Button("Speichern", e -> {
            order.setOrderStatus(orderStatus.getValue());
            orderService.saveOrder(order);
            updateList();
            dialog.close();
        });

        formLayout.add(orderStatus, saveButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private void updateList() {
        try {
            var articles = orderService.findAll();

            grid.setItems(articles);
            grid.getDataProvider().refreshAll();

        } catch (Exception e) {
            notificationService.showError("Fehler beim Laden der Bestellungen: " + e.getMessage());
        }
    }

}
