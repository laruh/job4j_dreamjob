package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.config.DataBaseConnection;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostStoreTest {

    private static PostDBStore store;

    @BeforeAll
    public static void init() {
        DataSource dataSource = new DataBaseConnection().loadPool();
        store = new PostDBStore(dataSource);
    }

    @AfterEach
    public void whenDeleteFrom() {
        store.deleteFrom();
    }

    @Test
    public void whenCreatePost() {
        City city = new City(1, "Москва");
        Post post = new Post(1, "Java", "Spring, Java 17", true, city);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertEquals(postInDb.getName(), post.getName());
    }

    @Test
    public void whenUpdatePost() {
        City city = new City(1, "Москва");
        Post post = new Post(1, "Middle", "Spring, Java 17", false, city);
        store.add(post);
        Post postUpd = new Post(1, "Senior", "Spring, Java 17", true, city);
        store.update(postUpd);
        Post postInDb = store.findById(post.getId());
        assertEquals(postInDb.getName(), postUpd.getName());
    }

    @Test
    public void whenFindAll() {
        City city = new City(1, "Москва");
        Post post1 = new Post(1, "Middle", "Java 17", false, city);
        Post post2 = new Post(2, "Senior", "Kotlin", true, city);
        store.add(post1);
        store.add(post2);
        List<Post> exp = List.of(new Post(1, "Middle", "Java 17", false, city),
                new Post(2, "Senior", "Kotlin", true, city));
        assertEquals(store.findAll(), exp);
    }
}