package com.galaxe;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxe.controller.PostController;
import com.galaxe.entity.CommentEntity;
import com.galaxe.entity.PostEntity;
import com.galaxe.repository.PostRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostController.class)
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
//		PostEntity post2 = new PostEntity(1L, "post 2", "desc 1", "content 1", set);
		
		CommentEntity comment = new CommentEntity(1L,"post 1 content 1",post1);
		set.add(comment);
		
		
		
		list = new ArrayList<>();
		list.add(post1);
//		list.add();

	}
	
	@Test
	public void testGetAllPosts() throws Exception {
		String url = "/api/posts";
//		String jsonValue = new ObjectMapper().writeValueAsString(list);
		Mockito.when(repository.findAll()).thenReturn(list);
		RequestBuilder rb = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult mr = mockMvc.perform(rb).andReturn();
		String actual = mr.getResponse().getContentAsString();
		System.out.println("result:"+actual);
		System.out.println("status:"+ mr.getResponse().getStatus());
	}

	@Test
	public void testCreatePost() throws Exception {
		String url = "/api/posts";
		String jsonValue = new ObjectMapper().writeValueAsString(new PostEntity(1L,"post 1", "desc 1", "content 1", null));
		Mockito.when(repository.save(new PostEntity("post 1", "desc 1", "content 1", null))).thenReturn(new PostEntity(1L,"post 1", "desc 1", "content 1", null));
		RequestBuilder rb = MockMvcRequestBuilders.post(url).content(jsonValue);
		MvcResult mr = mockMvc.perform(rb).andReturn();
		String actual = mr.getResponse().getContentAsString();
		System.out.println("result:"+actual);
		System.out.println("status:"+ mr.getResponse().getStatus());
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
