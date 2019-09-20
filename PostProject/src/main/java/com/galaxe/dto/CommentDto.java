package com.galaxe.dto;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

public class CommentDto {
	@Id
	private Long id;
	
	@NotEmpty
	@Lob
	private String text;

	private PostDto post;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PostDto getPost() {
		return post;
	}

	public void setPost(PostDto post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", post=" + post + "]";
	}

}