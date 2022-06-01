package com.howtographql.hackernews;

public class Vote {

    private final String id;
    private final String userId;
    private final String linkId;

    public Vote(String id, String userId, String linkId) {
        this.id = id;
        this.userId = userId;
        this.linkId = linkId;
    }

    public Vote(String userId, String linkId) {
        this(null, userId, linkId);
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLinkId() {
        return linkId;
    }

    @Override
    public String toString() {
        return "Vote{" +
            "id='" + id + '\'' +
            ", userId='" + userId + '\'' +
            ", linkId='" + linkId + '\'' +
            '}';
    }
}
