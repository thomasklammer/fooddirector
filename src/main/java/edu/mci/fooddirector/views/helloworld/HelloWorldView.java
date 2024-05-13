package edu.mci.fooddirector.views.helloworld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.Account;
import edu.mci.fooddirector.model.domain.CartItem;
import edu.mci.fooddirector.model.enums.ArticleCategory;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.model.services.UserService;
import edu.mci.fooddirector.views.MainLayout;

import java.util.ArrayList;

@PageTitle("Hello World")
@Route(value = "hello-world", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView(CartService cartService) {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {


            var article = new edu.mci.fooddirector.model.domain.Article();
            article.setName("Test");
            article.setDescription("Das ist Artikel mit der Nummer ");
            article.setArticleCategory(ArticleCategory.Dessert);
            article.setNetPrice(5.99);
            article.setTaxRate(20);


            cartService.addCartItem(article);



        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
