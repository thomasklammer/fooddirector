package edu.mci.fooddirector.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    private User user;
    private Validator validator;

    @BeforeEach
    void setUp() {
        user = new User();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(user.getDeliveryAddress());
    }

    @Test
    void getEmail() {
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void getPassword() {
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void getFirstName() {
        user.setFirstName("John");
        assertEquals("John", user.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("John");
        assertEquals("John", user.getFirstName());
    }

    @Test
    void getLastName() {
        user.setLastName("Doe");
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("Doe");
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void getDeliveryAddress() {
        Address address = mock(Address.class);
        user.setDeliveryAddress(address);
        assertEquals(address, user.getDeliveryAddress());
    }

    @Test
    void setDeliveryAddress() {
        Address address = mock(Address.class);
        user.setDeliveryAddress(address);
        assertEquals(address, user.getDeliveryAddress());
    }

    @Test
    void isAdmin() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    @Test
    void setAdmin() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    @Test
    void testValidation() {
        user.setEmail("invalid-email");
        user.setPassword("");
        user.setFirstName("");
        user.setLastName("");
        user.setDeliveryAddress(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}
