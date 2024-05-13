package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.views.MainLayout;
import org.hibernate.annotations.processing.Find;
import org.vaadin.lineawesome.LineAwesomeIcon;


@PageTitle("Bestellungen")
@Route(value = "orders", layout = MainLayout.class)

public class OrdersView extends Div {

    public OrdersView(){
        VerticalLayout layout = new VerticalLayout();

        layout.setWidth("auto");
        layout.setMargin(true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        layout.add(addOrderDetail());
        layout.add(addOrderDetail());
        layout.add(addOrderDetail());

        Button Btn_orderDetail = new Button("Details");
        Btn_orderDetail.setClassName("custom-button");
        Btn_orderDetail.setIcon(LineAwesomeIcon.INFO_CIRCLE_SOLID.create());
        Btn_orderDetail.addClickListener(
                e -> UI.getCurrent().navigate(OrderDetailsView.class)
        );

        HorizontalLayout layoutBtnDetail = new HorizontalLayout();
        layoutBtnDetail.setAlignItems(FlexComponent.Alignment.END);
        layoutBtnDetail.add(Btn_orderDetail);
        layout.add(layoutBtnDetail);

        Div container = new Div();
        container.add(layout);
        layout.add(Btn_orderDetail);

        setClassName("OrdersView");
        container.setClassName("OrdersViewContainer");

        add(container);
    }

    private Div addOrderDetail(){

        VerticalLayout innerlayout = new VerticalLayout();

        HorizontalLayout Header = new HorizontalLayout();
        HorizontalLayout Row2 = new HorizontalLayout();
        HorizontalLayout Row3 = new HorizontalLayout();
        Row3.setAlignItems(FlexComponent.Alignment.END);

        //Content
        Span OrderNumber = new Span("Bestellnummer: " + "123456");
        OrderNumber.setClassName("custom-span");
        Span OrderDate = new Span("Bestelldatum: " + " 01.01.2021");
        OrderDate.setClassName("custom-span");
        Span OrderStatus = new Span("Status: " + "In Bearbeitung");
        OrderStatus.setClassName("custom-span");
        Span OrderPayment = new Span("bezahlt mit " + "Paypal");
        OrderPayment.setClassName("custom-span");
        Span OrderArticles = new Span("Kebab, Pommes, Cola");
        OrderArticles.setClassName("custom-span");

        Header.add(OrderNumber);
        Header.add(OrderDate);
        Row2.add(OrderStatus);

        Row3.add(OrderArticles);
        Row3.add(OrderPayment);

        innerlayout.add(Header);
        innerlayout.add(Row2);
        innerlayout.add(Row3);


        //Stylesheet
        Div borderedDiv = new Div(innerlayout);
        borderedDiv.setClassName("custom-border");

        return borderedDiv;

    }

}