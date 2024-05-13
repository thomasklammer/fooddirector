package edu.mci.fooddirector.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.map.configuration.style.Icon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.views.helloworld.HelloWorldView;
import edu.mci.fooddirector.views.orders.OrdersView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        createFooter();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Fooddirector");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addClassNames("side-nav");

        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        nav.addItem(new SideNavItem("Bestellungen", OrdersView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create()));

        return nav;
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
