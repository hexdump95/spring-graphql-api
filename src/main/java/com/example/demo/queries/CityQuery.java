package com.example.demo.queries;

import com.example.demo.documents.City;
import com.example.demo.repositories.CityRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
class CityQuery implements GraphQLQueryResolver {
    private final CityRepository cityRepository;

    CityQuery(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CompletableFuture<List<City>> cities() {
        return cityRepository.findAll().collectList().toFuture();
    }

    public CompletableFuture<City> city(String id) {
        return cityRepository.findById(id).toFuture();
    }
}
