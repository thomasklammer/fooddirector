package edu.mci.fooddirector.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
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
import edu.mci.fooddirector.model.domain.Account;
import edu.mci.fooddirector.model.domain.Address;

@Route("register")
@PageTitle("Register | Vaadin CRM")
public class RegistrationView extends VerticalLayout {

    public RegistrationView() {
        addClassName("registration-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Registration");
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        EmailField emailField = new EmailField("Email");
        PasswordField passwordField = new PasswordField("Password");
        PasswordField repeatPasswordField = new PasswordField("Repeat Password");

        // Creating a horizontal line
        HorizontalLayout lineLayout = new HorizontalLayout();
        lineLayout.setWidthFull();
        lineLayout.setHeight("1px");
        lineLayout.getStyle().set("background-color", "#CCCCCC"); // Adjust color as needed

        TextField streetField = new TextField("Street");
        TextField cityField = new TextField("City");
        TextField zipCodeField = new TextField("Zip Code");
        TextField houseNumberField = new TextField("House Number");
        TextField additionalInfoField = new TextField("Additional Info");
        Button registerButton = new Button("Register");

        Binder<Account> binder = new Binder<>(Account.class);

        binder.forField(firstNameField).asRequired("First name is required").bind(Account::getFirstName, Account::setFirstName);
        binder.forField(lastNameField).asRequired("Last name is required").bind(Account::getLastName, Account::setLastName);
        binder.forField(emailField).asRequired("Email is required").bind(Account::getEmail, Account::setEmail);
        binder.forField(passwordField).asRequired("Password is required").bind(Account::getPassword, Account::setPassword);
        binder.forField(repeatPasswordField)
                .asRequired("Repeat Password is required")
                .withValidator(repeatPassword -> repeatPassword.equals(passwordField.getValue()), "Passwords must match")
                .bind(Account::getPassword, Account::setPassword);

        binder.forField(streetField).asRequired("Street is required").bind(account -> account.getDeliveryAddress().getStreet(), (account, street) -> account.getDeliveryAddress().setStreet(street));
        binder.forField(cityField).asRequired("City is required").bind(account -> account.getDeliveryAddress().getCity(), (account, city) -> account.getDeliveryAddress().setCity(city));
        binder.forField(zipCodeField).asRequired("Zip Code is required").bind(account -> account.getDeliveryAddress().getZipCode(), (account, zipCode) -> account.getDeliveryAddress().setZipCode(zipCode));
        binder.forField(houseNumberField).asRequired("House Number is required").bind(account -> account.getDeliveryAddress().getHouseNumber(), (account, houseNumber) -> account.getDeliveryAddress().setHouseNumber(houseNumber));
        binder.forField(additionalInfoField).bind(account -> account.getDeliveryAddress().getAdditionalInfo(), (account, additionalInfo) -> account.getDeliveryAddress().setAdditionalInfo(additionalInfo));

        registerButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                // Save the account object to the database or perform any other necessary actions
                Notification.show("Registration successful!");
            } else {
                Notification.show("Please fix the errors in the form.");
            }
        });

        add(title, firstNameField, lastNameField, emailField, passwordField, repeatPasswordField, lineLayout,
                streetField, cityField, zipCodeField, houseNumberField, additionalInfoField, registerButton);
    }
}
