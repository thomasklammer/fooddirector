package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.views.MainLayout;
import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Bestellungen")
@Route(value = "orderDetails", layout = MainLayout.class)

public class OrderDetailsView extends Div{

    public OrderDetailsView(){

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);

        layout.setWidth("auto");
        layout.setMargin(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        layout.add(addOrderDetail());
        layout.add(addOrderDetail());
        layout.add(addOrderDetail());

        Button Btn_orderDetail = new Button("zurück");
        Btn_orderDetail.setClassName("custom-button");
        Btn_orderDetail.setIcon(LineAwesomeIcon.INFO_CIRCLE_SOLID.create());
        Btn_orderDetail.addClickListener(
                e -> UI.getCurrent().navigate(OrdersView.class)
        );

        HorizontalLayout layoutBtnDetail = new HorizontalLayout();
        layoutBtnDetail.setAlignItems(FlexComponent.Alignment.END);
        layoutBtnDetail.add(Btn_orderDetail);
        layout.add(layoutBtnDetail);

        Div container = new Div();
        container.add(layout);
        layout.add(Btn_orderDetail);

        add(container);
    }

    private Div addOrderDetail(){

        VerticalLayout innerlayout = new VerticalLayout();
        innerlayout.setSpacing(false);

        HorizontalLayout Header = new HorizontalLayout();
        HorizontalLayout Row2 = new HorizontalLayout();
        HorizontalLayout Row3 = new HorizontalLayout();
        Row3.setAlignItems(FlexComponent.Alignment.END);

        //Content
        Span OrderNumber = new Span("Bestellnummer: " + "123456");
        OrderNumber.setClassName("custom-span");
        Span OrderDate = new Span("Bestelldatum: " + " 01.01.2021");
        OrderDate.setClassName("custom-span");
        Span OrderStatus = new Span("Lieferadresse: " + "Musterstraße1");
        OrderStatus.setClassName("custom-span");
        Span OrderPayment = new Span("Preis " + "20€");
        OrderPayment.setClassName("custom-span");
        Span OrderArticles = new Span("Mwst." + "20%");
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
