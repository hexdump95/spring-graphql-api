package com.example.demo.mutations;

import com.example.demo.documents.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class BookMutation implements GraphQLMutationResolver {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookMutation(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public CompletableFuture<Book> createBook(Book book){
        return bookRepository.save(book)
                .zipWith(authorRepository.findAllById(book.getAuthorsId()).collectList(),
                        (b, a) -> {
                            b.setAuthors(a);
                            return b;
                        })
                .toFuture();
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
