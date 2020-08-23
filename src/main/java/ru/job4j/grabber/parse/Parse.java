package ru.job4j.grabber.parse;

import ru.job4j.grabber.model.Post;

import java.io.IOException;
import java.util.List;

public interface Parse {
    List<Post> list(String link) throws IOException;

    Post detail(String link) throws IOException;
}
