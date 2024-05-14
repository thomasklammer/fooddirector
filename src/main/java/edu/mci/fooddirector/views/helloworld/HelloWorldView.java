package edu.mci.fooddirector.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.services.UserService;
import edu.mci.fooddirector.views.MainLayout;

@PageTitle("Hello World")
@Route(value = "hello-world", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)

public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView(UserService userService) {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {

            try {
                var user = new User();
                user.setFirstName("Thomas");
                user.setLastName("Klammer");
                user.setEmail("thomasklammer.1993@gmail.com");
                user.setPassword("123456");

                var deliveryAddress = new Address();
                deliveryAddress.setCity("Sillian");
                deliveryAddress.setStreet("Dorf");
                deliveryAddress.setHouseNumber("92G/14");
                deliveryAddress.setZipCode("9920");


                user.setDeliveryAddress(deliveryAddress);

                userService.saveUser(user);
                var users = userService.findAll();

                System.out.println("test");
            }catch(Exception ex) {
                System.out.println(ex.getMessage());
            }

        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
