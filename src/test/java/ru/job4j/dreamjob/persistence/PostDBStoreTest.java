package ru.job4j.dreamjob.persistence;

import org.junit.After;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostDBStoreTest {

    @After
    public void whenDeleteFrom() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        store.deleteFrom();
    }

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1, "Москва");
        Post post = new Post(1, "Java", "Spring, Java 17", true, city);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), equalTo(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1, "Москва");
        Post post = new Post(1, "Middle", "Spring, Java 17", false, city);
        store.add(post);
        Post postUpd = new Post(1, "Senior", "Spring, Java 17", true, city);
        store.update(postUpd);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), equalTo(postUpd.getName()));
    }

    @Test
    public void whenFindAll() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City(1, "Москва");
        Post post1 = new Post(1, "Middle", "Java 17", false, city);
        Post post2 = new Post(2, "Senior", "Kotlin", true, city);
        store.add(post1);
        store.add(post2);
        List<Post> exp = List.of(new Post(1, "Middle", "Java 17", false, city),
                new Post(2, "Senior", "Kotlin", true, city));
        assertThat(store.findAll(), equalTo(exp));
    }
}