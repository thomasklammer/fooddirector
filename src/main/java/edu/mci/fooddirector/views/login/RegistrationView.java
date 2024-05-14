package edu.mci.fooddirector.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.services.RegistrationService;

@Route("register")
@PageTitle("Register | Vaadin CRM")
public class RegistrationView extends VerticalLayout {

    private final RegistrationService registrationService;

    private TextField firstNameField = new TextField("First Name");
    private TextField lastNameField = new TextField("Last Name");
    private EmailField emailField = new EmailField("Email");
    private PasswordField passwordField = new PasswordField("Password");
    private PasswordField repeatPasswordField = new PasswordField("Repeat Password");
    private TextField streetField = new TextField("Street");
    private TextField houseNumberField = new TextField("House Number");
    private TextField cityField = new TextField("City");
    private TextField zipCodeField = new TextField("Zip Code");
    private TextField additionalInfoField = new TextField("Additional Info");

    public RegistrationView(RegistrationService registrationService) {
        this.registrationService = registrationService;
        setSizeFull();
        addClassName("registration-view");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Button registerButton = new Button("Registrieren");
        registerButton.addClickListener(event -> registerUser());

        HorizontalLayout nameLayout = new HorizontalLayout(firstNameField, lastNameField);
        nameLayout.setSpacing(true);

        HorizontalLayout passwordLayout = new HorizontalLayout(passwordField, repeatPasswordField);
        passwordLayout.setSpacing(true);

        HorizontalLayout addressLayout = new HorizontalLayout(streetField, houseNumberField);
        addressLayout.setSpacing(true);

        HorizontalLayout cityLayout = new HorizontalLayout(cityField, zipCodeField);
        cityLayout.setSpacing(true);

        add(
                nameLayout, emailField, passwordLayout,
                addressLayout, cityLayout, additionalInfoField,
                registerButton
        );
    }

    private void registerUser() {
        User newUser = new User();
        newUser.setFirstName(firstNameField.getValue());
        newUser.setLastName(lastNameField.getValue());
        newUser.setEmail(emailField.getValue());
        newUser.setPassword(passwordField.getValue());

        Address deliveryAddress = new Address();
        deliveryAddress.setStreet(streetField.getValue());
        deliveryAddress.setCity(cityField.getValue());
        deliveryAddress.setZipCode(zipCodeField.getValue());
        deliveryAddress.setHouseNumber(houseNumberField.getValue());
        deliveryAddress.setAdditionalInfo(additionalInfoField.getValue());

        newUser.setDeliveryAddress(deliveryAddress);

        boolean registrationSuccessful = registrationService.registerUser(newUser);
        if (registrationSuccessful) {
            Notification.show("Registration successful!");
        } else {
            Notification.show("Email address already exists. Registration failed.");
        }
    }
}