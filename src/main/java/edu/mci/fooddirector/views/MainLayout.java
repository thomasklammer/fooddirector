package edu.mci.fooddirector.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.map.configuration.style.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.security.SecurityService;
import edu.mci.fooddirector.views.cart.CartView;
import edu.mci.fooddirector.views.helloworld.HelloWorldView;
import edu.mci.fooddirector.views.orders.AdminOrdersView;
import edu.mci.fooddirector.views.orders.OrdersView;
import edu.mci.fooddirector.views.report.ReportView;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
@PermitAll
public class MainLayout extends AppLayout {

    private final SecurityService securityService;
    private H2 viewTitle;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        createFooter();
        getStyle().set("background-color", "#FBF7EF");
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Button logout = new Button("Log out", e ->securityService.logout());

        addToNavbar(true, toggle, viewTitle, logout);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Fooddirector");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);


        var navigation = createNavigation();
        var adminNavigation = createAdminNavigation();

        VerticalLayout navWrapper = new VerticalLayout(navigation, adminNavigation);
        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        navigation.setWidthFull();
        adminNavigation.setWidthFull();


        Scroller scroller = new Scroller(navWrapper);
        addToDrawer(header, scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addClassNames("side-nav");

        nav.getElement().getStyle().set("background-color", "#FBF7EF");

        String activeClass = "active-nav-item";

//        SideNavItem hw = new SideNavItem("HelloWorldView", HelloWorldView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create());
//        hw.getElement().getClassList().add(activeClass);
//        nav.addItem(hw);

        SideNavItem cartNavItem = new SideNavItem("Warenkorb", CartView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create());
        cartNavItem.getElement().getClassList().add(activeClass);
        nav.addItem(cartNavItem);

        SideNavItem ordersNavItem = new SideNavItem("Bestellungen", OrdersView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create());
        ordersNavItem.getElement().getClassList().add(activeClass);
        nav.addItem(ordersNavItem);


        return nav;
    }
     private SideNav createAdminNavigation() {
        SideNav adminNav = new SideNav();
        adminNav.setLabel("Admin");
        adminNav.setCollapsible(true);

        SideNavItem ordersNavItem = new SideNavItem("Bestellungen", AdminOrdersView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create());
        ordersNavItem.getElement().getClassList().add("active-nav-item");
        adminNav.addItem(ordersNavItem);

        SideNavItem reportNavItem = new SideNavItem("Bericht", ReportView.class, LineAwesomeIcon.PASTE_SOLID.create());
        reportNavItem.getElement().getClassList().add("active-nav-item");
        adminNav.addItem(reportNavItem);

        return adminNav;
    }

    private void createFooter() {
        Div div = new Div();
        div.addClassNames("footer");

        Footer footer = new Footer();
        footer.add(div);
        addToNavbar(false, footer);
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
