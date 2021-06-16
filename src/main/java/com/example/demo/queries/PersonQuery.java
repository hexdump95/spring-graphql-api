package com.example.demo.queries;

import com.example.demo.documents.Person;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.PersonRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class PersonQuery implements GraphQLQueryResolver {
    private final PersonRepository personRepository;
    private final CityRepository cityRepository;
    private final BookRepository bookRepository;

    public PersonQuery(PersonRepository personRepository, CityRepository cityRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.cityRepository = cityRepository;
        this.bookRepository = bookRepository;
    }

    public CompletableFuture<List<Person>> persons() {
        return personRepository.findAll().collectList().toFuture();
    }

    public CompletableFuture<Person> person(String id) {
        return personRepository.findById(id)
                .flatMap(p ->
                         Mono.just(p).zipWith(bookRepository.findAllById(p.getBooksId()).collectList(),
                         (person, books) -> {
                             person.setBooks(books);
                             return person;
                         })
                ).flatMap(p ->
                        cityRepository.findById(p.getAddress().getCityId())
                                .map(c -> {
                                    p.getAddress().setCity(c);
                                    return p;
                                })).toFuture();
    }

}
