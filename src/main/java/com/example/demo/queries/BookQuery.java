package com.example.demo.queries;

import com.example.demo.documents.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class BookQuery implements GraphQLQueryResolver {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    BookQuery(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public CompletableFuture<List<Book>> books() {
        return bookRepository.findAll().collectList().toFuture();
    }

    public CompletableFuture<Book> book(String id) {
        return bookRepository.findById(id)
                .flatMap(b ->  Mono.just(b).zipWith(authorRepository.findAllById(b.getAuthorsId()).collectList(),
                            (book, authors) -> {
                                book.setAuthors(authors);
                                return book;
                            })).toFuture();
    }
}
