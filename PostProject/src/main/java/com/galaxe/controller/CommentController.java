
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

import com.galaxe.dto.CommentDto;
import com.galaxe.entity.CommentEntity;
import com.galaxe.repository.CommentRepository;
import com.galaxe.repository.PostRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class CommentController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	private CommentEntity commentEntity;

	@GetMapping("/posts/{postId}/comments")
	public Page<CommentEntity> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable) {
		return commentRepository.findByPostId(postId, pageable);
	}

	@PostMapping("/posts/{postId}/comments")
	public CommentEntity createComment(@PathVariable(value = "postId") Long postId,
			@Valid @RequestBody CommentDto commentDto) {
		commentEntity = new CommentEntity();
		BeanUtils.copyProperties(commentDto, commentEntity);
		return postRepository.findById(postId).map(post -> {
			commentEntity.setPost(post);
			return commentRepository.save(commentEntity);
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public CommentEntity updateComment(@PathVariable(value = "postId") Long postId,

			@PathVariable(value = "commentId") Long commentId,

			@Valid @RequestBody CommentDto commentDtoUpdate) {
		if (!postRepository.existsById(postId)) {
			throw new ResourceNotFoundException("PostId " + postId + " not found");
		}
		commentEntity = new CommentEntity();
		BeanUtils.copyProperties(commentDtoUpdate, commentEntity);
		return commentRepository.findById(commentId).map(comment -> {
			comment.setText(commentDtoUpdate.getText());
			return commentRepository.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,

			@PathVariable(value = "commentId") Long commentId) {
		return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
			commentRepository.delete(comment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Comment not found with id " + commentId + " and postId " + postId));
	}
}
