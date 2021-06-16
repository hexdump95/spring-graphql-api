package com.example.demo.queries;

import com.example.demo.documents.Book;
import com.example.demo.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class BookQuery implements GraphQLQueryResolver {
    private final BookRepository bookRepository;

    BookQuery(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public CompletableFuture<List<Book>> books() {
        return bookRepository.findAll().collectList().toFuture();
    }

    public CompletableFuture<Book> book(String id) {
        return bookRepository.findById(id).toFuture();
    }
}
