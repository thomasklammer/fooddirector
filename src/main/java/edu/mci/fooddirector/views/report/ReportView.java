package edu.mci.fooddirector.views.report;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.charts.model.PlotOptionsErrorbar;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.views.MainLayout;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Random;

@PageTitle("Bericht | Fooddirector")
@Route(value = "report", layout = MainLayout.class)
public class ReportView extends VerticalLayout {

    public ReportView() {
        // Create and style the header
        H2 header = new H2("Bericht");
        header.addClassNames(
                LumoUtility.Margin.Bottom.NONE,
                LumoUtility.Margin.Top.NONE,
                LumoUtility.FontSize.XXXLARGE,
                LumoUtility.Margin.Bottom.XLARGE
        );

        // Create a scatter chart
        Chart chart = new Chart(ChartType.SCATTER);

        // Configure the chart
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Random Values per Month");
        conf.getLegend().setEnabled(false);

        // Generate the last 12 months
        String[] months = new String[12];
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < 12; i++) {
            months[11 - i] = currentDate.minusMonths(i).getMonth()
                    .getDisplayName(TextStyle.FULL, Locale.GERMAN);
        }

        // Set the x-axis labels to the last 12 months
        XAxis xAxis = new XAxis();
        xAxis.setCategories(months);
        conf.addxAxis(xAxis);

        // Configure y-axis
        YAxis yAxis = new YAxis();
        yAxis.setTitle("Wert (€)");
        yAxis.setMin(5000);
        yAxis.setMax(10000);
        conf.addyAxis(yAxis);

        // Generate random values for the y-axis
        Random random = new Random();
        Number[] values = new Number[12];
        for (int i = 0; i < 12; i++) {
            values[i] = 5000 + random.nextInt(5001); // random value between 5000 and 10000
        }

        // The primary data series for random values
        ListSeries randomValues = new ListSeries("Random Values", values);

        conf.addSeries(randomValues);

        // Add components to the layout
        add(header);
        add(chart);
    }
}
