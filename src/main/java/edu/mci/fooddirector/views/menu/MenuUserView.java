package edu.mci.fooddirector.views.menu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.enums.ArticleCategory;
import edu.mci.fooddirector.model.services.ArticleService;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.combobox.ComboBox;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "menu", layout = MainLayout.class)
@PageTitle("Speisekarte")
@PermitAll
public class MenuUserView extends VerticalLayout {

    private final ArticleService articleService;
    private final CartService cartService;
    private Grid<Article> grid = new Grid<>(Article.class);

    @Autowired
    public MenuUserView(ArticleService articleService, CartService cartService) {
        this.articleService = articleService;
        this.cartService = cartService;
        addClassName("menu-view");
        setSizeFull();
        configureGrid();
        addFilterButtons();
        updateList();
        add(grid);
    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addColumn(Article::getName).setHeader("Artikel");
        grid.addColumn(Article::getDescription).setHeader("Beschreibung");
        grid.addColumn(Article::getNetPrice).setHeader("Preis").setAutoWidth(true);
        grid.addComponentColumn(this::createAddToCartComponent).setHeader("");
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
    }

    private void updateListByCategory(ArticleCategory category) {
        List<Article> filteredArticles = articleService.findAll().stream()
                .filter(article -> category == null || article.getArticleCategory() == category)
                .collect(Collectors.toList());
        grid.setItems(filteredArticles);
    }

    private void addToCart(Article article, int quantity) {
        cartService.addCartItem(article, quantity);
        Notification.show(quantity + " " + article.getName() + "(s) zum Warenkorb hinzugefügt");
    }
}
