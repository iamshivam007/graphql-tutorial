package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    static MongoDatabase mongoDatabase = new MongoClient().getDatabase("hackernews");
    private final static LinkRepository linkRepository;
    private final static UserRepository userRepository;

    static {
        linkRepository = new LinkRepository(mongoDatabase.getCollection("links"));
        userRepository = new UserRepository(mongoDatabase.getCollection("users"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    @Override
    protected AuthContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
            .map(req -> req.getHeader("Authorization"))
            .filter(token -> !token.isEmpty())
            .map(token -> token.replace("Bearer ", ""))
            .map(userRepository::findById)
            .orElse(null);
        return new AuthContext(user, request, response);
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
            .file("schema.graphqls")
            .resolvers(
                new Query(linkRepository, userRepository),
                new Mutation(linkRepository, userRepository),
                new SigninResolver(),
                new LinkResolver(userRepository)
            )
            .build()
            .makeExecutableSchema();
    }
}
