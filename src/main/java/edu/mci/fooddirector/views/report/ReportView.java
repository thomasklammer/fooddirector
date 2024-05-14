package edu.mci.fooddirector.views.report;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.enums.OrderStatus;
import edu.mci.fooddirector.model.enums.PaymentMethod;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;

import java.time.LocalDateTime;
import java.util.List;


@PageTitle("Bericht | Fooddirector")
@Route(value = "report", layout = MainLayout.class)

public class ReportView extends VerticalLayout {
    public ReportView() {
        //addClassNames("report");

        H2 header = new H2("Bericht");
        header.addClassNames(LumoUtility.Margin.Bottom.NONE, LumoUtility.Margin.Top.NONE, LumoUtility.FontSize.XXXLARGE, LumoUtility.Margin.Bottom.XLARGE);

        //var content = new Main();
        //content.addClassNames("cart-grid", LumoUtility.Gap.XLARGE, LumoUtility.AlignItems.START, LumoUtility.JustifyContent.EVENLY, LumoUtility.MaxWidth.FULL);
        //content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.EVENLY, MaxWidth.FULL);


        add(header);
        //add(content);
    }
}
