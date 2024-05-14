package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Account;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import edu.mci.fooddirector.model.repositories.OrderDetailRepository;
import edu.mci.fooddirector.model.repositories.OrderRepository;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;
import org.hibernate.annotations.processing.Find;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@PageTitle("Bestellungen")
@Route(value = "orders", layout = MainLayout.class)

public class OrdersView extends Div {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrdersView(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository){

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);

        Address address1 = new Address();
        address1.setId(1L);
        address1.setCity("Wien");
        address1.setStreet("Teststraße");
        address1.setHouseNumber("1");
        address1.setZipCode("1010");
        address1.setAdditionalInfo("Top 1");

        Account user1 = new Account();
        user1.setId(2L);
        user1.setFirstName("Sepp");
        user1.setLastName("Müller");
        user1.setEmail("test@test.at");
        user1.setPassword("test");
        user1.setDeliveryAddress(address1);

        Order order1 = new Order();
        order1.setId(3L);
        order1.setOrderDate(LocalDateTime.now());
        order1.setOrderStatus(OrderStatus.Confirmed);
        order1.setPaymentMethod(PaymentMethod.PayPal);
        order1.setDeliveryAddress(address1);

        OrderService orderService = new OrderService(orderRepository,orderDetailRepository);
        orderService.saveOrder(order1);

        List<Order> orders = orderService.findAll();

        Grid<Order> grid = new Grid<>();
        grid.setItems(orders);
        grid.addColumn(Order::getId).setHeader("ID");
        grid.addColumn(Order::getOrderDate).setHeader("Bestelldatum");
        grid.addColumn(Order::getOrderDetails).setHeader("Artikel");
        grid.addColumn(Order::getOrderStatus).setHeader("Status");

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        SingleSelect<Grid<Order>, Order> orderSelect =
                grid.asSingleSelect();

        orderSelect.addValueChangeListener(e -> {
            Order selectedPerson = e.getValue();
        });

        grid.addItemClickListener(
                e -> UI.getCurrent().navigate(OrderDetailsView.class)
        );

        layout.add(grid);

        layout.setWidth("auto");
        layout.setMargin(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);


        Div container = new Div();
        container.add(layout);

        setClassName("OrdersView");
        container.setClassName("OrdersViewContainer");

        add(container);
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

}