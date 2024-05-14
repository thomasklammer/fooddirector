package edu.mci.fooddirector.model.services;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void showWarning(String message) {
        Notification notification = new Notification(
                message,
                3000,
                Notification.Position.TOP_START
        );
        notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        notification.open();
    }

    public void showError(String message) {
        Notification notification = new Notification(
                message,
                3000,
                Notification.Position.TOP_START
        );
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    public void showInfo(String message) {
        Notification notification = new Notification(
                message,
                3000,
                Notification.Position.TOP_START
        );
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        notification.open();
    }
}
