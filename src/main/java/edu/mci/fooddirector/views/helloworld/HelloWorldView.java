package edu.mci.fooddirector.views.helloworld;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import edu.mci.fooddirector.model.services.ArticleService;
import edu.mci.fooddirector.model.services.CartService;
import edu.mci.fooddirector.model.services.OrderService;
import edu.mci.fooddirector.views.MainLayout;

@PageTitle("Hello World")
@Route(value = "hello-world", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView(CartService cartService, ArticleService articleService, OrderService orderService) {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");




        sayHello.addClickListener(e -> {
            try {
                var article = articleService.findFirst();
                int quantity = 1;
                article.ifPresent(article1 -> cartService.addCartItem(article1, quantity));
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }



        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
