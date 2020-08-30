package ru.job4j.grabber.model;

import java.sql.Timestamp;

public class Post {
    private String id;
    private final String name;
    private final String text;
    private final String link;
    private final Timestamp created;

    public Post(String name, String text, String link, Timestamp created) {
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public Timestamp getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Post{"
                + "id='" + id + '\''
                + "name='" + name + '\''
                + ", text='" + text + '\''
                + ", link='" + link + '\''
                + ", created=" + created
                + '}';
    }
}
