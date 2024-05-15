package edu.mci.fooddirector.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import edu.mci.fooddirector.model.services.UserService;
import edu.mci.fooddirector.security.SecurityService;
import edu.mci.fooddirector.views.cart.CartView;
import edu.mci.fooddirector.views.menu.MenuAdminView;
import edu.mci.fooddirector.views.menu.MenuUserView;
import edu.mci.fooddirector.views.orders.AdminOrdersView;
import edu.mci.fooddirector.views.orders.OrdersView;
import edu.mci.fooddirector.views.report.ReportView;
import jakarta.annotation.security.PermitAll;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
@PermitAll
public class MainLayout extends AppLayout {

    private final SecurityService securityService;
    private final UserService userService;
    private H2 viewTitle;

    public MainLayout(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
        setPrimarySection(Section.DRAWER);

        addHeaderContent();
        addDrawerContent();
        createFooter();

        getStyle().set("background-color", "#FBF7EF");
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Button logout = new Button("Logout", e -> securityService.logout());


        H1 logo = new H1("Fooddirector - Gruppe 5");
        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo); // <4>
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

        toggle.addClickListener(event -> updateFooterPosition());
    }

    private void addDrawerContent() {
        var navigation = createNavigation();

        var currentUser = userService.getCurrentUser();

        VerticalLayout navWrapper;
        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            var adminNavigation = createAdminNavigation();
            adminNavigation.setWidthFull();
            navWrapper = new VerticalLayout(navigation, adminNavigation);
        } else {
            navWrapper = new VerticalLayout(navigation);
        }

        navWrapper.setSpacing(true);
        navWrapper.setSizeUndefined();
        navigation.setWidthFull();


        Scroller scroller = new Scroller(navWrapper);
        addToDrawer(scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addClassNames("side-nav");

        nav.getElement().getStyle().set("background-color", "#FBF7EF");

        String activeClass = "active-nav-item";


        SideNavItem menuNavItem = new SideNavItem("Speisekarte", MenuUserView.class, LineAwesomeIcon.UTENSILS_SOLID.create());
        menuNavItem.getElement().getClassList().add(activeClass);
        nav.addItem(menuNavItem);

        SideNavItem cartNavItem = new SideNavItem("Warenkorb", CartView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create());
        cartNavItem.getElement().getClassList().add(activeClass);
        nav.addItem(cartNavItem);

        SideNavItem ordersNavItem = new SideNavItem("Bestellungen", OrdersView.class, LineAwesomeIcon.FOLDER_SOLID.create());
        ordersNavItem.getElement().getClassList().add(activeClass);
        nav.addItem(ordersNavItem);


        return nav;
    }

    private SideNav createAdminNavigation() {
        SideNav adminNav = new SideNav();
        adminNav.setLabel("Admin");
        adminNav.setCollapsible(true);

        SideNavItem ordersNavItem = new SideNavItem("Bestellungen", AdminOrdersView.class, LineAwesomeIcon.FOLDER_SOLID.create());
        ordersNavItem.getElement().getClassList().add("active-nav-item");
        adminNav.addItem(ordersNavItem);


        SideNavItem menuManagementNavItem = new SideNavItem("Speisekarte Management", MenuAdminView.class, LineAwesomeIcon.UTENSILS_SOLID.create());
        menuManagementNavItem.getElement().getClassList().add("active-nav-item");
        adminNav.addItem(menuManagementNavItem);

        SideNavItem reportNavItem = new SideNavItem("Bericht", ReportView.class, LineAwesomeIcon.CHART_PIE_SOLID.create());
        reportNavItem.getElement().getClassList().add("active-nav-item");
        adminNav.addItem(reportNavItem);

        return adminNav;
    }

    private void createFooter() {
        Div div = new Div();
        div.addClassNames("footer");

        // Social Media Icons
        Anchor instagram = new Anchor("https://www.instagram.com", LineAwesomeIcon.INSTAGRAM.create());
        Anchor facebook = new Anchor("https://www.facebook.com", LineAwesomeIcon.FACEBOOK.create());
        Anchor twitter = new Anchor("https://www.twitter.com", LineAwesomeIcon.TWITTER.create());

        // Footer Texts
        Anchor impressum = new Anchor("#", "Impressum");
        Anchor kontakt = new Anchor("#", "Kontakt");
        Anchor agbs = new Anchor("#", "AGBs");

        div.add(instagram, facebook, twitter, impressum, kontakt, agbs);

        Footer footer = new Footer();
        footer.add(div);
        addToNavbar(false, footer);
    }

    private void updateFooterPosition() {
        if (getElement().getClassList().contains("drawer-closed")) {
            getElement().getClassList().remove("drawer-closed");
        } else {
            getElement().getClassList().add("drawer-closed");
        }
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
        //updateFooterPosition();
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}
