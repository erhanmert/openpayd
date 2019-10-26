package com.openpayd.openpayd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientDTO {

    private Long id;

    private String name;

    private String surname;

    private AddressDTO primaryAddress;

    private AddressDTO secondaryAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AddressDTO getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(AddressDTO primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public AddressDTO getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(AddressDTO secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }

}
