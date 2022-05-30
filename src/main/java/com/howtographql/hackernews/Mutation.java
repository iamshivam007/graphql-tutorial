package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

import java.util.Optional;


public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public Link createLink(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        Link newLink = new Link(
            url,
            description,
            Optional.ofNullable(context.getUser()).map(User::getId).orElse(null));
        linkRepository.saveLink(newLink);
        System.out.println(newLink);
        return newLink;
    }

    public User createUser(String name, AuthData authData) {
        User newUser = new User(name, authData.getEmail(), authData.getPassword());
        userRepository.saveUser(newUser);
        System.out.println(newUser);
        return newUser;
    }

    public SigninPayload signinUser(AuthData authData) {
        User user = userRepository.findByEmail(authData.getEmail());
        if (user.getPassword().equals(authData.getPassword())) {
            return new SigninPayload(user, user.getId());
        }
        throw new GraphQLException("Invalid user");
    }
}
