package edu.mci.fooddirector.views.menu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Article;
import edu.mci.fooddirector.model.enums.ArticleCategory;
import edu.mci.fooddirector.model.services.ArticleService;
import edu.mci.fooddirector.model.services.NotificationService;
import edu.mci.fooddirector.util.ArticleCategoryToStringConverter;
import edu.mci.fooddirector.util.DoubleToStringConverter;
import edu.mci.fooddirector.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

@Route(value = "admin/articles", layout = MainLayout.class)
@PageTitle("Speisekarte Management | Fooddirector")
@RolesAllowed("ADMIN")
public class MenuAdminView extends VerticalLayout {

    private final ArticleService articleService;
    private final NotificationService notificationService;
    private final Grid<Article> grid = new Grid<>(Article.class);


    @Autowired
    public MenuAdminView(ArticleService articleService, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.articleService = articleService;
        setSizeFull();

        addClassName("padding-bottom");

        Button addArticleButton = new Button("Neuen Artikel anlegen", e -> {
            var dummyArticle = new Article();
            dummyArticle.setName("");
            dummyArticle.setTaxRate(20);
            dummyArticle.setDescription("");
            dummyArticle.setDailyOffer(false);
            dummyArticle.setImage("");
            dummyArticle.setDiscount(0);

            openEditDialog(dummyArticle);
        });
        addArticleButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        setSizeFull();
        configureGrid();
        updateList();
        add(grid);

        HorizontalLayout toolbar = new HorizontalLayout(addArticleButton);
        toolbar.setWidthFull();
        toolbar.setPadding(true);


        add(toolbar);
    }


    private void configureGrid() {
        grid.removeAllColumns();
        grid.setHeight("auto");

        grid.addColumn(Article::getName)
                .setHeader("Name")
                .setAutoWidth(true);
        grid.addColumn(x -> DoubleToStringConverter.convertToCurrency(x.getNetPrice()))
                .setHeader("Nettopreis")
                .setAutoWidth(true);
        grid.addColumn(x -> DoubleToStringConverter.convertToPercentage(x.getTaxRate()))
                .setHeader("Steuer")
                .setAutoWidth(true);
        grid.addColumn(Article::getDescription).setHeader("Beschreibung")
                .setAutoWidth(true);
        grid.addColumn(article -> ArticleCategoryToStringConverter.convert(article.getArticleCategory()))
                .setHeader("Kategorie")
                .setAutoWidth(true);
        grid.addColumn(article -> article.isDailyOffer() ? "Ja" : "Nein")
                .setHeader("Tagesangebot")
                .setAutoWidth(true);
        grid.addColumn(x -> DoubleToStringConverter.convertToPercentage(x.getDiscount()))
                .setHeader("Rabatt")
                .setAutoWidth(true);

        grid.addComponentColumn(this::createButtons).setHeader("Aktionen")
                .setAutoWidth(true);

    }

    private HorizontalLayout createButtons(Article article) {
        return new HorizontalLayout(createEditButton(article), createDeleteButton(article));
    }


    private Button createDeleteButton(Article article) {
        Button editButton = new Button("Löschen", e -> {

            try {
                articleService.deleteArticle(article);
                updateList();
            }
            catch(Exception ex) {
                notificationService.showError("Löschen des Artikels fehlgeschlagen");
            }

        });
        editButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return editButton;
    }


    private Button createEditButton(Article article) {
        Button editButton = new Button("Bearbeiten", event -> openEditDialog(article));
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return editButton;
    }

    private void updateList() {
        try {
            List<Article> articles = articleService.findAll();
            if (articles.isEmpty()) {
                notificationService.showWarning("Keine Artikel gefunden.");
            } else {
                grid.setItems(articles);
                grid.getDataProvider().refreshAll();
                System.out.println("Artikel erfolgreich geladen und im Grid gesetzt.");
            }
        } catch (Exception e) {
            notificationService.showError("Fehler beim Laden der Artikel: " + e.getMessage());
        }
    }

    private void openEditDialog(Article article) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Name");
        nameField.setValue(article.getName());

        TextField priceField = new TextField("Netto Preis");
        priceField.setValue(String.valueOf(article.getNetPrice()));

        TextField taxRateField = new TextField("Steuer");
        taxRateField.setValue(String.valueOf(article.getTaxRate()));

        TextField descriptionField = new TextField("Beschreibung");
        descriptionField.setValue(article.getDescription());

        ComboBox<ArticleCategory> categoryComboBox = new ComboBox<>("Kategorie", ArticleCategory.values());
        categoryComboBox.setValue(article.getArticleCategory());

        Checkbox dailyOfferCheckbox = new Checkbox("Tagesangebot");
        dailyOfferCheckbox.setValue(article.isDailyOffer());

        TextField discountField = new TextField("Rabatt");
        discountField.setValue(String.valueOf(article.getDiscount()));


        TextField imageField = new TextField("Link zum Bild");
        imageField.setValue(String.valueOf(article.getImage()));

        Button saveButton = new Button("Speichern", e -> {
            try {
                article.setName(nameField.getValue());
                article.setNetPrice(Double.parseDouble(priceField.getValue()));
                article.setTaxRate(Double.parseDouble(taxRateField.getValue()));
                article.setDescription(descriptionField.getValue());
                article.setArticleCategory(categoryComboBox.getValue());
                article.setDailyOffer(dailyOfferCheckbox.getValue());
                article.setDiscount(Double.parseDouble(discountField.getValue()));
                article.setImage(imageField.getValue());

                articleService.saveArticle(article);
                updateList();
                dialog.close();
            } catch (Exception ex) {
                notificationService.showError("Fehler beim Speichern des Artikels: " + ex.getMessage());
            }

        });

        Button cancelButton = new Button("Abbrechen", e -> dialog.close());

        var horizontalLayout = new HorizontalLayout(saveButton, cancelButton);

        formLayout.add(nameField, priceField, taxRateField, descriptionField, categoryComboBox, imageField, dailyOfferCheckbox, discountField, horizontalLayout);
        dialog.add(formLayout);
        dialog.open();
    }

}
