package com.timecoder.web;

import com.timecoder.dto.PostDto;
import com.timecoder.util.Page;
import com.timecoder.model.Post;
import com.timecoder.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public Iterable<Post> getAllPosts(Page page) {
        Sort sort = Sort.by(new Sort.Order(page.getOrderBy(), page.getSortBy()));

        PageRequest pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize(),sort);

        return postService.getAllPosts(pageRequest);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id).get();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity createPost(@Valid @RequestBody PostDto post) {
        long postId = postService.createPost(post);
        return new ResponseEntity<>(singletonMap("created", postId), OK);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public ResponseEntity updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        postService.getPostById(id);
        long postId = postService.updatePost(post);
        return new ResponseEntity<>(singletonMap("updated", postId), OK);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePostById(@PathVariable("id") Long id) {
        boolean deleted = postService.deletePostById(id);
        return new ResponseEntity<>(singletonMap("changed", deleted), OK);
    }
}
