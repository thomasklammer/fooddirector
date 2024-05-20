package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


    public Optional<User> getCurrentUser() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        if (currentPrincipalName.isBlank()) {
            return Optional.empty();
        }

        return userRepository.findByEmail(currentPrincipalName);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
