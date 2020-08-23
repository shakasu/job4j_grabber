package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;

import java.util.List;

public interface Store {
    void save(Post post) throws ClassNotFoundException;

    List<Post> getAll();
}
