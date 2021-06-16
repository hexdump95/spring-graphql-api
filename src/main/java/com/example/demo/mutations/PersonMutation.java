package com.example.demo.mutations;

import com.example.demo.documents.Person;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.PersonRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class PersonMutation implements GraphQLMutationResolver {
    private final PersonRepository personRepository;
    private final CityRepository cityRepository;

    public PersonMutation(PersonRepository personRepository, CityRepository cityRepository) {
        this.personRepository = personRepository;
        this.cityRepository = cityRepository;
    }

    public CompletableFuture<Person> createPerson(Person person){
        return personRepository.save(person)
                .flatMap(p ->
                        cityRepository.findById(p.getAddress().getCityId())
                                .map(c -> {
                                    p.getAddress().setCity(c);
                                    return p;
                                })
                )
                .toFuture();
    }

    public CompletableFuture<Person> updatePerson(String id, Person person){
        return personRepository.findById(id)
                .flatMap(p -> {
                    person.setId(id);
                    return personRepository.save(person);
                }).toFuture();
    }

    public CompletableFuture<Boolean> deletePerson(String id) {
        return personRepository.findById(id)
                .defaultIfEmpty(new Person())
                .flatMap(person -> {
                    if(person.getId() == null)
                        return Mono.just(false);
                    return personRepository.delete(person).then(Mono.just(true));
                }).toFuture();
    }
}
