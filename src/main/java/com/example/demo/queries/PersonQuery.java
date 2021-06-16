package com.example.demo.queries;

import com.example.demo.documents.Person;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.PersonRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class PersonQuery implements GraphQLQueryResolver {
    private final PersonRepository personRepository;
    private final CityRepository cityRepository;

    public PersonQuery(PersonRepository personRepository, CityRepository cityRepository) {
        this.personRepository = personRepository;
        this.cityRepository = cityRepository;
    }

    public CompletableFuture<List<Person>> persons() {
        return personRepository.findAll().collectList().toFuture();
    }

    public CompletableFuture<Person> person(String id) {
        return personRepository.findById(id)
                .flatMap(p ->
                    cityRepository.findById(p.getAddress().getCityId())
                    .map(c -> {
                        p.getAddress().setCity(c);
                        return p;
                    })
                )
                .toFuture();
    }
}
