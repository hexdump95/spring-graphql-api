package com.example.demo.mutations;

import com.example.demo.documents.Book;
import com.example.demo.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class BookMutation implements GraphQLMutationResolver {
    private final BookRepository bookRepository;

    public BookMutation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public CompletableFuture<Book> createBook(Book book){
        return bookRepository.save(book).toFuture();
    }

    public CompletableFuture<Book> updateBook(String id, Book book){
        return bookRepository.findById(id)
                .flatMap(b -> {
                    book.setId(id);
                    return bookRepository.save(book);
                }).toFuture();
    }

    public CompletableFuture<Boolean> deleteBook(String id) {
        return bookRepository.findById(id)
                .defaultIfEmpty(new Book())
                .flatMap(book -> {
                    if(book.getId() == null)
                        return Mono.just(false);
                    return bookRepository.delete(book).then(Mono.just(true));
                }).toFuture();
    }
}
