package edu.mci.fooddirector.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.services.RegistrationService;
import jakarta.annotation.security.PermitAll;

@Route("register")
@PageTitle("Register | Vaadin CRM")
@PermitAll
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    public RegistrationView() {
        addClassName("registration-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Registration");

        // User Information Section
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        HorizontalLayout nameLayout = new HorizontalLayout(firstNameField, lastNameField);
        nameLayout.setSpacing(true);
        EmailField emailField = new EmailField("Email");

        HorizontalLayout passwordLayout = new HorizontalLayout();
        PasswordField passwordField = new PasswordField("Password");
        PasswordField repeatPasswordField = new PasswordField("Repeat Password");
        passwordLayout.add(passwordField, repeatPasswordField);
        passwordLayout.setSpacing(true);

        // Address Information Section
        TextField streetField = new TextField("Street");
        TextField houseNumberField = new TextField("House Number");

        HorizontalLayout addressLayout = new HorizontalLayout(streetField, houseNumberField);
        addressLayout.setSpacing(true);

        TextField cityField = new TextField("City");
        TextField zipCodeField = new TextField("Zip Code");
        TextField additionalInfoField = new TextField("Additional Info");

        VerticalLayout addressInfoLayout = new VerticalLayout(addressLayout, cityField, zipCodeField, additionalInfoField);
        addressInfoLayout.setSpacing(true);
        addressInfoLayout.setAlignItems(Alignment.CENTER);

        // Register Button
        Button registerButton = new Button("Register");

        Binder<User> binder = new Binder<>(User.class);

        binder.forField(firstNameField).asRequired("First name is required").bind(User::getFirstName, User::setFirstName);
        binder.forField(lastNameField).asRequired("Last name is required").bind(User::getLastName, User::setLastName);
        binder.forField(emailField).asRequired("Email is required").bind(User::getEmail, User::setEmail);
        binder.forField(passwordField).asRequired("Password is required").bind(User::getPassword, User::setPassword);
        binder.forField(repeatPasswordField)
                .asRequired("Repeat Password is required")
                .withValidator(repeatPassword -> repeatPassword.equals(passwordField.getValue()), "Passwords must match")
                .bind(User::getPassword, User::setPassword);

        binder.forField(streetField).asRequired("Street is required").bind(account -> account.getDeliveryAddress().getStreet(), (account, street) -> account.getDeliveryAddress().setStreet(street));
        binder.forField(cityField).asRequired("City is required").bind(account -> account.getDeliveryAddress().getCity(), (account, city) -> account.getDeliveryAddress().setCity(city));
        binder.forField(zipCodeField).asRequired("Zip Code is required").bind(account -> account.getDeliveryAddress().getZipCode(), (account, zipCode) -> account.getDeliveryAddress().setZipCode(zipCode));
        binder.forField(houseNumberField).asRequired("House Number is required").bind(account -> account.getDeliveryAddress().getHouseNumber(), (account, houseNumber) -> account.getDeliveryAddress().setHouseNumber(houseNumber));
        binder.forField(additionalInfoField).bind(account -> account.getDeliveryAddress().getAdditionalInfo(), (account, additionalInfo) -> account.getDeliveryAddress().setAdditionalInfo(additionalInfo));

        Button backToLoginButton = new Button("Back to Login");
        backToLoginButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)));

        registerButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                // Save the user object to the database or perform any other necessary actions
                Notification.show("Registration successful!");
            } else {
                Notification.show("Please fix the errors in the form.");
            }
        });

        add(title, nameLayout, emailField, passwordLayout, addressInfoLayout, registerButton, backToLoginButton);
    }
}