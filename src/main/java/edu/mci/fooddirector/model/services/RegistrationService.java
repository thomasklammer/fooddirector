package edu.mci.fooddirector.model.services;

import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.User;
import edu.mci.fooddirector.model.repositories.AddressRepository;
import edu.mci.fooddirector.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public boolean registerUser(User user) {
        // Check if the email address already exists in the database
        if (userRepository.existsByEmail(user.getEmail())) {
            // Email address already exists, registration failed
            return false;
        }

        // Email address doesn't exist, proceed with registration
        // Save the delivery address first
        Address deliveryAddress = user.getDeliveryAddress();
        deliveryAddress = addressRepository.save(deliveryAddress);

        // Set the delivery address in the user object
        user.setDeliveryAddress(deliveryAddress);

        // Save the user
        userRepository.save(user);

        // Registration successful
        return true;
    }
}
