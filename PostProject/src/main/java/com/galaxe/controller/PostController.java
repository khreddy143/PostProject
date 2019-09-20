package com.galaxe.controller;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galaxe.dto.PostDto;
import com.galaxe.entity.PostEntity;
import com.galaxe.repository.PostRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping(value="/api", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    
    private PostEntity postEntity;
    
    @GetMapping("/posts")
    public Page<PostEntity> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @PostMapping("/posts")
    public PostEntity createPost(@Valid @RequestBody PostDto post) {
    	postEntity = new PostEntity();
    	BeanUtils.copyProperties(post,postEntity);
        return postRepository.save(postEntity);
    }

    @PutMapping("/posts/{postId}")
    public PostEntity updatePost(@PathVariable Long postId, @Valid @RequestBody PostDto postRequest) {
    	postEntity = new PostEntity();
    	BeanUtils.copyProperties(postRequest,postEntity);
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postEntity.getTitle());
            post.setDescription(postEntity.getDescription());
            post.setContent(postEntity.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

}