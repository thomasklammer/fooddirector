package edu.mci.fooddirector.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.services.NotificationService;
import edu.mci.fooddirector.model.services.UserService;

@Route("register")
@PageTitle("Registrieren | Fooddirector")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    public RegistrationView(NotificationService notificationService, UserService userService) {
        addClassName("registration-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Registrierung");

        // User Information Section
        TextField firstNameField = new TextField("Vorname");
        TextField lastNameField = new TextField("Nachnamme");
        HorizontalLayout nameLayout = new HorizontalLayout(firstNameField, lastNameField);
        nameLayout.setSpacing(true);
        EmailField emailField = new EmailField("Email");

        HorizontalLayout passwordLayout = new HorizontalLayout();
        PasswordField passwordField = new PasswordField("Passwort");
        PasswordField repeatPasswordField = new PasswordField("Password wiederholen");
        passwordLayout.add(passwordField, repeatPasswordField);
        passwordLayout.setSpacing(true);

        // Address Information Section
        TextField streetField = new TextField("Straße");
        TextField houseNumberField = new TextField("Hausnummer");

        HorizontalLayout addressLayout = new HorizontalLayout(streetField, houseNumberField);
        addressLayout.setSpacing(true);

        TextField cityField = new TextField("Stadt");
        TextField zipCodeField = new TextField("Postleitzahl");
        TextField additionalInfoField = new TextField("Zusatzinfo");

        VerticalLayout addressInfoLayout = new VerticalLayout(addressLayout, cityField, zipCodeField, additionalInfoField);
        addressInfoLayout.setSpacing(true);
        addressInfoLayout.setAlignItems(Alignment.CENTER);

        // Register Button
        Button registerButton = new Button("Registrieren");

        Binder<User> binder = new Binder<>(User.class);

        var user = new User();
        binder.setBean(user);

        binder.forField(firstNameField).asRequired("Pflichtfeld").bind(User::getFirstName, User::setFirstName);
        binder.forField(lastNameField).asRequired("Pflichtfeld").bind(User::getLastName, User::setLastName);
        binder.forField(emailField).asRequired("Pflichtfeld").bind(User::getEmail, User::setEmail);
        binder.forField(passwordField).asRequired("Pflichtfeld").bind(User::getPassword, User::setPassword);
        binder.forField(repeatPasswordField)
                .asRequired("Pflichtfeld")
                .withValidator(repeatPassword -> repeatPassword.equals(passwordField.getValue()), "Passwörter stimmen nicht überein")
                .bind(User::getPassword, User::setPassword);

        binder.forField(streetField).asRequired("Pflichtfeld").bind(account -> account.getDeliveryAddress().getStreet(), (account, street) -> account.getDeliveryAddress().setStreet(street));
        binder.forField(cityField).asRequired("Pflichtfeld").bind(account -> account.getDeliveryAddress().getCity(), (account, city) -> account.getDeliveryAddress().setCity(city));
        binder.forField(zipCodeField).asRequired("Pflichtfeld").bind(account -> account.getDeliveryAddress().getZipCode(), (account, zipCode) -> account.getDeliveryAddress().setZipCode(zipCode));
        binder.forField(houseNumberField).asRequired("Pflichtfeld").bind(account -> account.getDeliveryAddress().getHouseNumber(), (account, houseNumber) -> account.getDeliveryAddress().setHouseNumber(houseNumber));
        binder.forField(additionalInfoField).bind(account -> account.getDeliveryAddress().getAdditionalInfo(), (account, additionalInfo) -> account.getDeliveryAddress().setAdditionalInfo(additionalInfo));

        Button backToLoginButton = new Button("Zurück zum Login");
        backToLoginButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));

        registerButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                userService.saveUser(user);

                notificationService.showSuccess("Registrierung erfolgreich");
                getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            } else {
                notificationService.showWarning("Bitte die Fehler im Formular korrigieren");
            }
        });

        add(title, nameLayout, emailField, passwordLayout, addressInfoLayout, registerButton, backToLoginButton);
    }
}