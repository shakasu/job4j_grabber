package ru.job4j.grabber.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String name;
    private String text;
    private String link;
    private Timestamp created;

    public Post(int id, String name, String text, String link, Timestamp created) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    public Post(String name, String text, String link, Timestamp created) {
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
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

//    @Override
//    public String toString() {
//        return "Post{" +
//                "id='" + id + '\'' +
//                "name='" + name + '\'' +
//                ", text='" + text + '\'' +
//                ", link='" + link + '\'' +
//                ", created=" + created +
//                '}';
//    }
}
