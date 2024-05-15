package edu.mci.fooddirector.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import edu.mci.fooddirector.views.login.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/images/**");
        super.configure(web);
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("test@test.at")
                .password("{noop}123456")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin@test.at")
                .password("{noop}123456")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user,admin);
    }
}
