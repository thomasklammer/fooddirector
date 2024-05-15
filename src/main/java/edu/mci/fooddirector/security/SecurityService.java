package edu.mci.fooddirector.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.hibernate.annotations.Comment;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    public void logout(){
        UI.getCurrent().getPage().setLocation("/");
        SecurityContextLogoutHandler logOutHandler = new SecurityContextLogoutHandler();
        logOutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
