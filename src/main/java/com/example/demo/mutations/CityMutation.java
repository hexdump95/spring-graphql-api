package com.example.demo.mutations;

import com.example.demo.documents.City;
import com.example.demo.repositories.CityRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class CityMutation implements GraphQLMutationResolver {
    private final CityRepository cityRepository;

    public CityMutation(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CompletableFuture<City> createCity(City city){
        return cityRepository.save(city).toFuture();
    }

    public CompletableFuture<City> updateCity(String id, City city){
        return cityRepository.findById(id)
                .flatMap(c -> {
                    city.setId(id);
                    return cityRepository.save(city);
                }).toFuture();
    }

    public CompletableFuture<Boolean> deleteCity(String id) {
        return cityRepository.findById(id)
                .defaultIfEmpty(new City())
                .flatMap(city -> {
                    if(city.getId() == null)
                        return Mono.just(false);
                    return cityRepository.delete(city).then(Mono.just(true));
                }).toFuture();
    }
}
