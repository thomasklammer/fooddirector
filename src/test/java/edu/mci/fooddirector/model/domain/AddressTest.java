package edu.mci.fooddirector.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address;
    private Validator validator;

    @BeforeEach
    void setUp() {
        address = new Address();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getCity() {
        address.setCity("Vienna");
        assertEquals("Vienna", address.getCity());
    }

    @Test
    void setCity() {
        address.setCity("Vienna");
        assertEquals("Vienna", address.getCity());
    }

    @Test
    void getStreet() {
        address.setStreet("Main Street");
        assertEquals("Main Street", address.getStreet());
    }

    @Test
    void setStreet() {
        address.setStreet("Main Street");
        assertEquals("Main Street", address.getStreet());
    }

    @Test
    void getZipCode() {
        address.setZipCode("1010");
        assertEquals("1010", address.getZipCode());
    }

    @Test
    void setZipCode() {
        address.setZipCode("1010");
        assertEquals("1010", address.getZipCode());
    }

    @Test
    void getHouseNumber() {
        address.setHouseNumber("123");
        assertEquals("123", address.getHouseNumber());
    }

    @Test
    void setHouseNumber() {
        address.setHouseNumber("123");
        assertEquals("123", address.getHouseNumber());
    }

    @Test
    void getAdditionalInfo() {
        address.setAdditionalInfo("Apt 4B");
        assertEquals("Apt 4B", address.getAdditionalInfo());
    }

    @Test
    void setAdditionalInfo() {
        address.setAdditionalInfo("Apt 4B");
        assertEquals("Apt 4B", address.getAdditionalInfo());
    }

    @Test
    void testValidation() {
        Address invalidAddress = new Address();
        invalidAddress.setCity("");
        invalidAddress.setStreet("");
        invalidAddress.setZipCode("");
        invalidAddress.setHouseNumber("");

        Set<ConstraintViolation<Address>> violations = validator.validate(invalidAddress);
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }
}
