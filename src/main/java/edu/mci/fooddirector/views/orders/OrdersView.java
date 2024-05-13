package edu.mci.fooddirector.views.orders;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.views.MainLayout;
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

    public Div addOrderDetail(){

        VerticalLayout innerlayout = new VerticalLayout();

        HorizontalLayout Header = new HorizontalLayout();
        HorizontalLayout Row2 = new HorizontalLayout();
        HorizontalLayout Row3 = new HorizontalLayout();
        Row3.setAlignItems(FlexComponent.Alignment.END);

        //Content
        Span OrderNumber = new Span("Bestellnummer: " + "123456");
        Span OrderDate = new Span("Bestelldatum: " + " 01.01.2021");
        Span OrderStatus = new Span("Status: " + "In Bearbeitung");
        Span OrderArticles = new Span("Bananenbrot");

        Header.add(OrderNumber);
        Header.add(OrderDate);
        Row2.add(OrderStatus);

        Row3.add(OrderArticles);

        innerlayout.add(Header);
        innerlayout.add(Row2);
        innerlayout.add(Row3);


        //Stylesheet
        Div borderedDiv = new Div(innerlayout);
        borderedDiv.setClassName("custom-border");

        return borderedDiv;

    }

}