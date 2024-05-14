package edu.mci.fooddirector.views.report;

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
import java.util.List;


@PageTitle("Bericht")
@Route(value = "report", layout = MainLayout.class)

public class ReportView {
}
