package com.example.demo.mutations;

import com.example.demo.documents.Author;
import com.example.demo.repositories.AuthorRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class AuthorMutation implements GraphQLMutationResolver {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorMutation(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public CompletableFuture<Author> createAuthor(Author author){
        return authorRepository.save(author).toFuture();
    }

    public CompletableFuture<Author> updateAuthor(String id, Author author){
        return authorRepository.findById(id)
                .flatMap(a -> {
                            modelMapper.map(author, a);
                            return authorRepository.save(a);
                        }
                ).toFuture();
    }

    public CompletableFuture<Boolean> deleteAuthor(String id) {
        return authorRepository.findById(id)
                .defaultIfEmpty(new Author())
                .flatMap(a -> {
                    if(a.getId() == null)
                        return Mono.just(false);
                    return authorRepository.delete(a).then(Mono.just(true));
                }).toFuture();
    }
}
