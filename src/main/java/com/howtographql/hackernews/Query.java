package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.Link;
import com.howtographql.hackernews.LinkRepository;

import java.util.ArrayList;
import java.util.List;

public class Query implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Query(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }
    public List<User> allUsers() {
        return userRepository.getAllUsers();
    }
    public List<Vote> allVotes() {
        return voteRepository.getAllVotes();
    }

}
