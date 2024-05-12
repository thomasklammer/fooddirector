package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Account;
import edu.mci.fooddirector.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void saveUser(Account user) {
        userRepository.save(user);
    }

    public List<Account> findAll() {
        return userRepository.findAll();
    }
}
