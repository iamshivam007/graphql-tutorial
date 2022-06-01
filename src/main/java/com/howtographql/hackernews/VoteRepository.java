package com.howtographql.hackernews;

import com.mongodb.client.MongoCollection;
import graphql.Scalars;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class VoteRepository {

    private final MongoCollection<Document> votes;

    public VoteRepository(MongoCollection<Document> votes) {
        this.votes = votes;
    }

    public Vote findById(String id) {
        Document document = votes.find(eq("_id", new ObjectId(id))).first();
        return vote(document);
    }

    public List<Vote> findByLinkId(String linkId) {
        List<Vote> allVotes = new ArrayList<>();
        for(Document document: votes.find(eq("linkId", linkId))) {
            allVotes.add(vote(document));
        }
        return allVotes;
    }

    public List<Vote> getAllVotes() {
        List<Vote> allVotes = new ArrayList<>();
        for(Document document: votes.find()) {
            allVotes.add(vote(document));
        }
        return allVotes;
    }

    public void saveVote(Vote vote) {
        Document document = new Document();
        document.put("linkId", vote.getLinkId());
        document.put("userId", vote.getUserId());
        votes.insertOne(document);
    }

    private Vote vote(Document document) {
        if (document == null) {
            return null;
        }
        return new Vote(
                document.get("_id").toString(),
                document.getString("userId"),
                document.getString("linkId"));
    }
}
