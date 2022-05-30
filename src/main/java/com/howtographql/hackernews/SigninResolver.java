package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public User user(SigninPayload signinPayload) {
        return signinPayload.getUser();
    }

}
