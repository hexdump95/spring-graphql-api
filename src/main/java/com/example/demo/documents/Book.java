package com.example.demo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document
public class Book {
    @Id
    private String id;
    private String title;
    private Date date;
    private int pages;
    private String genre;
    private List<String> authorsId = new ArrayList<>();
    @Transient
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(String id, String title, Date date, int pages, String genre, List<String> authorsId, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.pages = pages;
        this.genre = genre;
        this.authorsId = authorsId;
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<String> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<String> authorsId) {
        this.authorsId = authorsId;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(date, book.date) && Objects.equals(genre, book.genre) && Objects.equals(authorsId, book.authorsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, pages, genre, authorsId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", pages=" + pages +
                ", genre='" + genre + '\'' +
                ", authorsId=" + authorsId +
                '}';
    }
}
