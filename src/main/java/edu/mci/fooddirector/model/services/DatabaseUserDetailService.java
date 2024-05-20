package edu.mci.fooddirector.model.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailService implements UserDetailsService {

    private final UserService userService;

    public DatabaseUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userService.getUserByEmail(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Benutzer mit der E-Mail " + username + " nicht gefunden");
        }

        var user = optionalUser.get();

        return User.withUsername(user.getEmail())
                .password("{noop}" + user.getPassword())
                .roles(user.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}
