package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        MongoDatabase mongoDatabase = new MongoClient().getDatabase("hackernews");
        LinkRepository linkRepository = new LinkRepository(mongoDatabase.getCollection("links"));
        UserRepository userRepository = new UserRepository(mongoDatabase.getCollection("users"));
        return SchemaParser.newParser()
            .file("schema.graphqls")
            .resolvers(
                new Query(linkRepository, userRepository),
                new Mutation(linkRepository, userRepository),
                new SigninResolver()
            )
            .build()
            .makeExecutableSchema();
    }
}
