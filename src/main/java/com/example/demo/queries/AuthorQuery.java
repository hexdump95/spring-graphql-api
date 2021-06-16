package com.example.demo.queries;

import com.example.demo.documents.Author;
import com.example.demo.repositories.AuthorRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
class AuthorQuery implements GraphQLQueryResolver {
    private final AuthorRepository authorRepository;

    AuthorQuery(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public CompletableFuture<List<Author>> authors() {
        return authorRepository.findAll().collectList().toFuture();
    }

    public CompletableFuture<Author> author(String id) {
        return authorRepository.findById(id).toFuture();
    }
}
