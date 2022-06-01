package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class LinkResolver implements GraphQLResolver<Link> {

    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public LinkResolver(UserRepository userRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public User postedBy(Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepository.findById(link.getUserId());
    }

    public int voteCount(Link link) {
        return voteRepository.findByLinkId(link.getId().toString()).size();
    }

}
