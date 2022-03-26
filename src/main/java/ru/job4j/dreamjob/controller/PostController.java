package ru.job4j.dreamjob.controller;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.services.CityService;
import ru.job4j.dreamjob.services.PostService;

@ThreadSafe
@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/postsDB")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "postsDB";
    }

    @GetMapping("/addPostDB")
    public String addPost(Model model) {
        return "addPostDB";
    }

    @GetMapping("/updatePostDB/{postId}")
    public String updatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postService.findById(id));
        return "updatePostDB";
    }

    @PostMapping("/savePostDB")
    public String savePost(@ModelAttribute Post post) {
        postService.add(post);
        return "redirect:/postsDB";
    }

    @PostMapping("/updatePostDB")
    public String updatePost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/postsDB";
    }
}
