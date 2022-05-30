package com.howtographql.hackernews;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final MongoCollection<Document> users;

    public UserRepository(MongoCollection<Document> users) {
        this.users = users;
    }

    public User findByEmail(String email) {
        Document document = users.find(eq("email", email)).first();
        return user(document);
    }

    public User findById(String id) {
        Document document = users.find(eq("_id", id)).first();
        return user(document);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for(Document document: users.find()) {
            allUsers.add(user(document));
        }
        return allUsers;
    }

    public void saveUser(User user) {
        Document document = new Document();
        document.put("name", user.getName());
        document.put("email", user.getEmail());
        document.put("password", user.getPassword());
        users.insertOne(document);
    }

    private User user(Document document) {
        if (document == null) {
            return null;
        }
        return new User(
                document.get("_id").toString(),
                document.getString("name"),
                document.getString("email"),
                document.getString("password"));
    }
}