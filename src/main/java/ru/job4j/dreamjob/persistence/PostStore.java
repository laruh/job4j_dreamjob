package ru.job4j.dreamjob.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public void add(Post post) {
        int newId = id.incrementAndGet();
        post.setId(newId);
        posts.putIfAbsent(newId, post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
