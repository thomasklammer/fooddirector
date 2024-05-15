package edu.mci.fooddirector.views.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.enums.ArticleCategory;
import edu.mci.fooddirector.model.services.ArticleService;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.model.services.NotificationService;
import edu.mci.fooddirector.util.DoubleToStringConverter;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;


import java.util.List;
import java.util.stream.Collectors;


@Route(value = "menu", layout = MainLayout.class)
@PageTitle("Speisekarte | Fooddirector")
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class MenuUserView extends VerticalLayout {

    private final ArticleService articleService;
    private final CartService cartService;
    private final NotificationService notificationService;
    private final Grid<Article> grid = new Grid<>(Article.class);

    private VerticalLayout categoryFilters;


    public MenuUserView(ArticleService articleService,
                        CartService cartService,
                        NotificationService notificationService) {

        this.articleService = articleService;
        this.cartService = cartService;
        this.notificationService = notificationService;
        addClassName("menu-view");
        addClassName("padding-bottom");
        setSizeFull();

        categoryFilters = createCategoryFilters(); // Ensure this is initialized first
        categoryFilters.setWidth("200px");

        configureGrid();
        updateList();



        HorizontalLayout mainLayout = new HorizontalLayout(categoryFilters, grid);
        mainLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        mainLayout.setSizeFull();
        mainLayout.expand(grid); // This makes the grid take up the remaining space

        add(createHeader("Tagesangebote"));
        add(createDailySpecials());
        add(new Hr());
        add(createHeader("Speisekarte"));
        add(mainLayout); // Only add mainLayout which contains both categoryFilters and grid
    }



    private Component createHeader(String content) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        header.add(new H1(content));
        return header;
    }

    private Component createDailySpecials() {
        List<Article> dailyOffers = articleService.findAll().stream()
                .filter(Article::isDailyOffer)
                .collect(Collectors.toList());

        HorizontalLayout specialsLayout = new HorizontalLayout();
        specialsLayout.setWidthFull();
        specialsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        specialsLayout.setPadding(true);

        for (Article offer : dailyOffers) {
            specialsLayout.add(createSpecial(offer.getName() + " - " + offer.getDescription(), offer.getGrossPriceDiscounted(), "path/to/image.jpg")); // Adjust image path accordingly
        }

        return specialsLayout;
    }


    private Component createSpecial(String description, double price, String imageUrl) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);
        layout.setPadding(false);
        layout.setSpacing(false);

        // Image handling
//        Image image = new Image(imageUrl, "menu item image");
//        image.setMaxHeight("150px");  // Set the maximum height for images
//        image.getStyle().set("border-radius", "8px");  // Optional: style images with rounded corners

        // Description as a label
        Span descLabel = new Span(description);
        descLabel.addClassNames("text-center", "text-md");  // Use helper classes for styling

        // Price label
        Span priceLabel = new Span(DoubleToStringConverter.convertToCurrency(price));
        priceLabel.addClassNames("text-center", "text-lg", "price-label");  // Additional class for price styling

        // Add components to the layout
        layout.add(descLabel, priceLabel);

        return layout;
    }

    private VerticalLayout createCategoryFilters() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setWidthFull();

        for (ArticleCategory category : ArticleCategory.values()) {
            Button button = new Button(category.toString(), e -> {
                updateListByCategory(category);
                highlightActiveCategory(category);
            });
            button.setWidthFull();
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            layout.add(button);
        }
        return layout;
    }



    private void configureGrid() {
        grid.removeAllColumns();
        grid.setHeight("auto");
        grid.addColumn(Article::getName).setHeader("Artikel").setAutoWidth(true);
        grid.addColumn(Article::getDescription).setHeader("Beschreibung").setAutoWidth(true);


        grid.addColumn(x -> DoubleToStringConverter.convertToCurrency(x.getGrossPriceDiscounted())).setHeader("Preis").setAutoWidth(true);
        grid.addComponentColumn(this::createAddToCartComponent).setHeader("Anzahl").setAutoWidth((true)).setAutoWidth(true);
        grid.getStyle().set("border", "none");
    }


    private HorizontalLayout createAddToCartComponent(Article article) {
        ComboBox<Integer> quantitySelector = new ComboBox<>();
        quantitySelector.setItems(1, 2, 3, 4, 5);
        quantitySelector.setValue(1); // Standardmäßig 1 als Menge ausgewählt

        Button addToCartButton = new Button("In den Warenkorb");
        addToCartButton.addClickListener(event -> {
            int quantity = quantitySelector.getValue();
            addToCart(article, quantity);
        });

        return new HorizontalLayout(quantitySelector, addToCartButton);
    }

    private void addFilterButtons() {
        Select<ArticleCategory> categoryFilter = new Select<>();
        categoryFilter.setLabel("Kategorie");
        categoryFilter.setItems(ArticleCategory.values());
        categoryFilter.setEmptySelectionAllowed(true);
        categoryFilter.addValueChangeListener(e -> updateListByCategory(e.getValue()));
        add(categoryFilter);
    }

    private void updateList() {
        grid.setItems(articleService.findAll());
        // Optionally highlight the first category or a default one
        if (categoryFilters.getComponentCount() > 0) {
            ((Button)categoryFilters.getComponentAt(0)).click();  // Simulate a click on the first category
        }
    }

    /*private HorizontalLayout createCategoryFilters() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.setJustifyContentMode(JustifyContentMode.START);

        for (ArticleCategory category : ArticleCategory.values()) {
            Button button = new Button(category.toString());
            button.addClickListener(e -> updateListByCategory(category));
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            layout.add(button);
        }

        return layout;
    }*/

    private void updateListByCategory(ArticleCategory category) {
        List<Article> filteredArticles = articleService.findAll().stream()
                .filter(article -> article.getArticleCategory() == category)
                .collect(Collectors.toList());
        grid.setItems(filteredArticles);
        highlightActiveCategory(category);
    }

    private void highlightActiveCategory(ArticleCategory activeCategory) {
        for (int i = 0; i < categoryFilters.getComponentCount(); i++) {
            Button button = (Button) categoryFilters.getComponentAt(i);
            if (button.getText().equals(activeCategory.toString())) {
                button.getStyle().set("font-weight", "bold");
                button.getStyle().set("color", "#1E90FF");  // Set the color to blue
            } else {
                button.getStyle().clear();
                button.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);  // Optionally revert to the default styling
            }
        }
    }
    private void addToCart(Article article, int quantity) {
        cartService.addCartItem(article, quantity);
        notificationService.showInfo(quantity + " " + article.getName() + "(s) zum Warenkorb hinzugefügt");
    }


}