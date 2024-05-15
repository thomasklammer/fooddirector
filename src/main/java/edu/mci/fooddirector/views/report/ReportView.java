package edu.mci.fooddirector.views.report;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.PlotOptionsColumn;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.domain.OrderDetail;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@PageTitle("Bericht | Fooddirector")
@Route(value = "report", layout = MainLayout.class)
@PermitAll
public class ReportView extends VerticalLayout {

    public ReportView(OrderService orderService) {
        // Create and style the header
        H2 header = new H2("Bericht");
        header.addClassNames(
                LumoUtility.Margin.Bottom.NONE,
                LumoUtility.Margin.Top.NONE,
                LumoUtility.FontSize.XXXLARGE,
                LumoUtility.Margin.Bottom.XLARGE
        );

        // Create a bar chart
        Chart chart = new Chart(ChartType.COLUMN);

        // Get the current Date
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // Configure the chart
        Configuration conf = chart.getConfiguration();
        conf.setTitle((currentYear - 1) + " - " + currentYear);
        conf.getLegend().setEnabled(true);

        // Generate the last 13 months
        String[] months = getLast13Months();

        // Set the x-axis labels to the last 13 months
        XAxis xAxis = new XAxis();
        xAxis.setCategories(months);
        conf.addxAxis(xAxis);

        // Configure primary y-axis
        YAxis yAxis1 = new YAxis();
        yAxis1.setTitle("Monatsumsatz (€)");
        conf.addyAxis(yAxis1);

        // Configure secondary y-axis
        YAxis yAxis2 = new YAxis();
        yAxis2.setTitle("Bestellungen");
        yAxis2.setOpposite(true); // Position on the right
        conf.addyAxis(yAxis2);

        // Monthly Data
        Number[] monthlySales = new Number[13];
        Number[] monthlyOrders = new Number[13];
        for (int i = 0; i < 13; i++) {
            int month = currentMonth + i;
            int year = currentYear - 1;
            if (month > 12) {
                month -= 12;
                year = currentYear;
            }
            // Sum Amount of monthly Orders
            List<Order> tmpOrders = orderService.findByMonthAndYear(month, year);
            if (!tmpOrders.isEmpty()) {
                monthlySales[i] = tmpOrders.stream().mapToDouble(Order::getOrderValue).sum();
                monthlyOrders[i] = (long) tmpOrders.size();
            } else {
                monthlySales[i] = 0;
                monthlyOrders[i] = 0;
            }
        }

        // Create the primary data series
        ListSeries series1 = new ListSeries("Monatsumsatz (€)", monthlySales);
        ListSeries series2 = new ListSeries("Bestellungen", monthlyOrders);

        // Set different colors for each series
        PlotOptionsColumn plotOptions1 = new PlotOptionsColumn();
        plotOptions1.setColor(new SolidColor("#1f77b4"));
        DataLabels dataLabels1 = new DataLabels(true);
        dataLabels1.setFormatter("function() { return '€ ' + this.y.toLocaleString('de-DE', {minimumFractionDigits: 0, maximumFractionDigits: 0}); }");
        plotOptions1.setDataLabels(dataLabels1);
        series1.setPlotOptions(plotOptions1);
        series1.setyAxis(0); // Left Y-Achse

        PlotOptionsColumn plotOptions2 = new PlotOptionsColumn();
        plotOptions2.setColor(new SolidColor("#ff7f0e"));
        DataLabels dataLabels2 = new DataLabels(true);
        plotOptions2.setDataLabels(dataLabels2);
        series2.setPlotOptions(plotOptions2);
        series2.setyAxis(1); // Right Y-Achse

        // Add the series to the configuration
        conf.addSeries(series1);
        conf.addSeries(series2);

        // Create export buttons
        Button pdfButton = new Button("Export as PDF", event -> exportToPdf(months, monthlySales, monthlyOrders));
        Button csvButton = new Button("Export as CSV", event -> exportToCsv(months, monthlySales, monthlyOrders));

        // Add components to the layout
        add(header);
        add(chart);
        add(pdfButton);
        add(csvButton);
    }

    private void exportToPdf(String[] months, Number[] values1, Number[] values2) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Monatsbericht"));
            for (int i = 0; i < months.length; i++) {
                document.add(new Paragraph(months[i] + ": €" + values1[i].toString() + ", Bestellungen: " + values2[i].toString()));
            }

            document.close();

            StreamResource resource = new StreamResource("report.pdf", () -> new ByteArrayInputStream(outputStream.toByteArray()));
            Anchor downloadLink = new Anchor(resource, "Download PDF");
            downloadLink.getElement().setAttribute("download", true);
            add(downloadLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportToCsv(String[] months, Number[] values1, Number[] values2) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(outputStream), CSVFormat.DEFAULT.withHeader("Monat", "Monatsumsatz (€)", "Bestellungen"));

            for (int i = 0; i < months.length; i++) {
                printer.printRecord(months[i], values1[i], values2[i]);
            }

            printer.flush();

            StreamResource resource = new StreamResource("report.csv", () -> new ByteArrayInputStream(outputStream.toByteArray()));
            Anchor downloadLink = new Anchor(resource, "Download CSV");
            downloadLink.getElement().setAttribute("download", true);
            add(downloadLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] getLast13Months() {
        String[] months = new String[13];
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < 13; i++) {
            months[12 - i] = currentDate.minusMonths(i).getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);
        }
        return months;
    }
}
