package com.galaxe.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")
public class PostEntity extends AuditModel{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(max = 100)
	@Column(unique = true)
	private String title;

	@NotEmpty
	@Size(max = 250)
	private String description;

	@NotEmpty
	@Lob
	private String content;

//	@JsonIgnore
//	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
	private Set<CommentEntity> comments = new HashSet<>();
	
	public PostEntity() {
		super();
	}

	public PostEntity(Long id, @NotEmpty @Size(max = 100) String title, @NotEmpty @Size(max = 250) String description,
			@NotEmpty String content, Set<CommentEntity> comments) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
	}
	
	public PostEntity(@NotEmpty @Size(max = 100) String title, @NotEmpty @Size(max = 250) String description,
			@NotEmpty String content, Set<CommentEntity> comments) {
		super();
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(Set<CommentEntity> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", comments=" + comments + "]";
	}

}