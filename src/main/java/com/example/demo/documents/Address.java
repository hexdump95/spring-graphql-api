package com.example.demo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Address {
    @Id
    private String id;
    private String street;
    private Integer number;
    private String cityId;
    @Transient
    private City city;

    public Address() {
    }

    public Address(String id, String street, Integer number, String cityId, City city) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.cityId = cityId;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(number, address.number) && Objects.equals(cityId, address.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, number, cityId);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", cityId='" + cityId + '\'' +
                '}';
    }
}
