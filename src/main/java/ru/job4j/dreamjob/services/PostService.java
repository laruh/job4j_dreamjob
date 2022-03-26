package ru.job4j.dreamjob.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostDataBaseStore;

import java.util.Collection;

@ThreadSafe
@Service
public class PostService {
    private final PostDataBaseStore store;

    public PostService(PostDataBaseStore store) {
        this.store = store;
    }

    public void add(Post post) {
        store.add(post);
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}
