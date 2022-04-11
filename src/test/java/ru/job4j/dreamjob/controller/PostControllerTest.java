package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.services.CityService;
import ru.job4j.dreamjob.services.PostService;


import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @Test
    void whenAllPosts() {
        City city = new City(1, "Moscow");
        User user = new User(1, "User");
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "post", true, city),
                new Post(2, "New post", "post", true, city));
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        HttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertEquals("posts", page);
    }

    @Test
    void whenAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "city"),
                new City(2, "city"));
        User user = new User(1, "User");
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );
        HttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("user", user);
        assertEquals("addPost", page);
    }

    @Test
    void whenUpdatePost() {
        City city = new City(1, "Moscow");
        Post post = new Post(1, "New post", "post", true, city);
        List<City> cities = Arrays.asList(
                new City(1, "city"),
                new City(2, "city"));
        User user = new User(1, "User");
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findById(1)).thenReturn(post);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );
        HttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        String page = postController.updatePost(model, 1, session);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("user", user);
        assertEquals("updatePost", page);
    }
}