package edu.mci.fooddirector.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="tbAddresses")
public class Address extends AbstractEntity {
    @NotBlank
    @Column(name="city")
    private String city;

    @NotBlank
    @Column(name="street")
    private String street;

    @NotBlank
    @Column(name="zipcode")
    private String zipCode;

    @NotBlank
    @Column(name="housenumber")
    private String houseNumber;

    @Column(name="additionalinfo")
    private String additionalInfo;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
