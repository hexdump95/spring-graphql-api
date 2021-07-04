package com.example.demo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Author {
    private String firstName;
    private String lastName;
    private String biography;
    private Address address;
    private List<Book> books;

    public Author() {
    }

    public Author(String firstName, String lastName, String biography, Address address, List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.address = address;
        this.books = books;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(biography, author.biography) && Objects.equals(address, author.address) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, biography, address, books);
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", biography='" + biography + '\'' +
                ", address=" + address +
                ", books=" + books +
                '}';
    }
}
