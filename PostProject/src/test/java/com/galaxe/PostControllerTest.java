package com.galaxe;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.galaxe.entity.CommentEntity;
import com.galaxe.entity.PostEntity;
import com.galaxe.repository.PostRepository;

public class PostControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PostRepository repository;

	private static List<PostEntity> list;
	private static Set<CommentEntity> set;
	
	@BeforeClass
	public static void setupData() {
		set = new HashSet<>();
//		set.add(new CommentEntity("post 1 comment 1", post1));
		PostEntity post1 = new PostEntity(1L, "post 1", "desc 1", "content 1", set);
		PostEntity post2 = new PostEntity(1L, "post 2", "desc 1", "content 1", set);
		
		
		list = new ArrayList<>();
//		list.add();
//		list.add();

	}
	
	@Test
	public void testGetAllPosts() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCreatePost() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUpdatePost() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDeletePost() {
		fail("Not yet implemented"); // TODO
	}

}
