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
import edu.mci.fooddirector.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.notification.Notification;
import java.util.List;

@Route(value = "admin/articles", layout = MainLayout.class)
@PageTitle("Speisekarte Management")
public class MenuAdminView extends VerticalLayout {

    private final ArticleService articleService;
    private Grid<Article> grid = new Grid<>(Article.class);




    @Autowired
    public MenuAdminView(ArticleService articleService) {
        this.articleService = articleService;
        setSizeFull();

        Button addArticleButton = new Button("Neuen Artikel anlegen", e -> openCreateArticleDialog());
        addArticleButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Grid<Article> articleGrid = createArticleGrid();
        setSizeFull();
        configureGrid();
        updateList();
        add(grid);

        HorizontalLayout toolbar = new HorizontalLayout(addArticleButton);
        toolbar.setWidthFull();
        toolbar.setPadding(true);

        FormLayout articleForm = createArticleForm();
        articleForm.setVisible(false); //

        add(toolbar);
    }



    private void configureGrid() {
        grid.removeAllColumns();

        grid.addColumn(Article::getName).setHeader("Name");
        grid.addColumn(Article::getNetPrice).setHeader("Net Price");
        grid.addColumn(Article::getTaxRate).setHeader("Tax Rate");
        grid.addColumn(Article::getDescription).setHeader("Description");
        grid.addColumn(article -> article.getArticleCategory().toString()).setHeader("Category");
        grid.addColumn(Article::isDailyOffer).setHeader("Daily Offer");
        grid.addColumn(Article::getDiscount).setHeader("Discount");

        grid.addComponentColumn(article -> createEditButton(article)).setHeader("Actions");
    }

    private Grid<Article> createArticleGrid() {
        Grid<Article> grid = new Grid<>(Article.class);
        configureGrid();
        return grid;
    }


    private Button createEditButton(Article article) {
        Button editButton = new Button("Edit", event -> openEditDialog(article));
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return editButton;
    }

    private void updateList() {
        try {
            List<Article> articles = articleService.findAll();
            if (articles.isEmpty()) {
                Notification.show("Keine Artikel gefunden.");
            } else {
                grid.setItems(articles);
                grid.getDataProvider().refreshAll();
                System.out.println("Artikel erfolgreich geladen und im Grid gesetzt.");
            }
        } catch (Exception e) {
            Notification.show("Fehler beim Laden der Artikel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openEditDialog(Article article) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Name");
        nameField.setValue(article.getName());

        TextField priceField = new TextField("Net Price");
        priceField.setValue(String.valueOf(article.getNetPrice()));

        TextField taxRateField = new TextField("Tax Rate");
        taxRateField.setValue(String.valueOf(article.getTaxRate()));

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(article.getDescription());

        ComboBox<ArticleCategory> categoryComboBox = new ComboBox<>("Category", ArticleCategory.values());
        categoryComboBox.setValue(article.getArticleCategory());

        Checkbox dailyOfferCheckbox = new Checkbox("Daily Offer");
        dailyOfferCheckbox.setValue(article.isDailyOffer());

        TextField discountField = new TextField("Discount");
        discountField.setValue(String.valueOf(article.getDiscount()));

        Button saveButton = new Button("Save", e -> {
            article.setName(nameField.getValue());
            article.setNetPrice(Double.parseDouble(priceField.getValue()));
            article.setTaxRate(Double.parseDouble(taxRateField.getValue()));
            article.setDescription(descriptionField.getValue());
            article.setArticleCategory(categoryComboBox.getValue());
            article.setDailyOffer(dailyOfferCheckbox.getValue());
            article.setDiscount(Double.parseDouble(discountField.getValue()));

            articleService.saveArticle(article);
            updateList();
            dialog.close();
        });

        formLayout.add(nameField, priceField, taxRateField, descriptionField, categoryComboBox, dailyOfferCheckbox, discountField, saveButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private void openCreateArticleDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Name");
        TextField priceField = new TextField("Net Price");
        TextField taxRateField = new TextField("Tax Rate");
        TextField descriptionField = new TextField("Description");
        ComboBox<ArticleCategory> categoryComboBox = new ComboBox<>("Category", ArticleCategory.values());
        Checkbox dailyOfferCheckbox = new Checkbox("Daily Offer");
        TextField discountField = new TextField("Discount");

        Button saveButton = new Button("Speichern", event -> {
            Article article = new Article();
            article.setName(nameField.getValue());
            article.setNetPrice(Double.parseDouble(priceField.getValue()));
            article.setTaxRate(Double.parseDouble(taxRateField.getValue()));
            article.setDescription(descriptionField.getValue());
            article.setArticleCategory(categoryComboBox.getValue());
            article.setDailyOffer(dailyOfferCheckbox.getValue());
            article.setDiscount(Double.parseDouble(discountField.getValue()));

            articleService.saveArticle(article);
            updateList();
            dialog.close();
        });

        Button cancelButton = new Button("Abbrechen", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        formLayout.add(nameField, priceField, taxRateField, descriptionField, categoryComboBox, dailyOfferCheckbox, discountField, saveButton, cancelButton);
        dialog.add(formLayout);
        dialog.open();
    }


    private FormLayout createArticleForm() {
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Name");
        TextField priceField = new TextField("Net Price");
        TextField taxRateField = new TextField("Tax Rate");
        TextField descriptionField = new TextField("Description");
        ComboBox<ArticleCategory> categoryComboBox = new ComboBox<>("Category", ArticleCategory.values());
        Checkbox dailyOfferCheckbox = new Checkbox("Daily Offer");
        TextField discountField = new TextField("Discount");

        Button saveButton = new Button("Save", event -> {
            Article article = new Article();
            article.setName(nameField.getValue());
            article.setNetPrice(Double.parseDouble(priceField.getValue()));
            article.setTaxRate(Double.parseDouble(taxRateField.getValue()));
            article.setDescription(descriptionField.getValue());
            article.setArticleCategory(categoryComboBox.getValue());
            article.setDailyOffer(dailyOfferCheckbox.getValue());
            article.setDiscount(Double.parseDouble(discountField.getValue()));

            articleService.saveArticle(article);
            updateList();
            clearForm(nameField, priceField, taxRateField, descriptionField, categoryComboBox, dailyOfferCheckbox, discountField);
        });


        formLayout.add(nameField, priceField, taxRateField, descriptionField, categoryComboBox, dailyOfferCheckbox, discountField, saveButton);
        return formLayout;
    }

    // Methode zum Löschen des Formulars nach dem Speichern
    private void clearForm(HasValue<?, ?>... fields) {
        for (HasValue<?, ?> field : fields) {
            field.clear();
        }
    }
}
