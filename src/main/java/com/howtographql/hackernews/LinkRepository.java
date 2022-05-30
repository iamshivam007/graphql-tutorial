package com.howtographql.hackernews;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

public class LinkRepository {

    private final MongoCollection<Document> links;

    public LinkRepository(MongoCollection<Document> links) {
        this.links = links;
    }

    public Link findById(String id) {
        Document document = links.find(eq("_id", new ObjectId(id))).first();
        return link(document);
    }

    public List<Link> getAllLinks() {
        List<Link> allLinks = new ArrayList<>();
        for(Document document: links.find()) {
            allLinks.add(link(document));
        }
        return allLinks;
    }

    public void saveLink(Link link) {
        Document document = new Document();
        document.put("url", link.getUrl());
        document.put("description", link.getDescription());
        document.put("postedBy", link.getUserId());
        links.insertOne(document);
    }

    private Link link(Document document) {
        return new Link(
                document.get("_id").toString(),
                document.getString("url"),
                document.getString("description"),
                document.getString("postedBy"));
    }
}