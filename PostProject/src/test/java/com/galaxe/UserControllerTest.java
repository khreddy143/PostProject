package com.galaxe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxe.controller.UserController;
import com.galaxe.entity.UserEntity;
import com.galaxe.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserRepository repository;

	private static List<UserEntity> list;
//	private Optional<User> user = Optional.empty();

	@BeforeClass
	public static void setupData() {
		list = new ArrayList<>();

		list.add(new UserEntity(1L, "hanumareddy", "", "hanuma", "reddy", 23, 20000D));
		list.add(new UserEntity(2L, "anilkumar", "", "anil", "kumar", 26, 25000D));

//		Optional.of(new User(1L,"hanumareddy","","hanuma","reddy",23,20000D));
	}

	@Test
	public void testGetInfo() throws Exception {
		String url = "/api/user/getAll";
		Mockito.when(repository.findAll()).thenReturn(list);
		RequestBuilder rb = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult mr = mockMvc.perform(rb).andReturn();
		String str = mr.getResponse().getContentAsString();
		System.out.println(mr.getResponse().getStatus());
		System.out.println(str);
		String er = "[{\"id\":1,\"username\":\"hanumareddy\",\"password\":\"\",\"firstName\":\"hanuma\",\"lastName\":\"reddy\",\"age\":23,\"salary\":20000.0},"
				+ "{\"id\":2,\"username\":\"anilkumar\",\"password\":\"\",\"firstName\":\"anil\",\"lastName\":\"kumar\",\"age\":26,\"salary\":25000.0}]";
		JSONAssert.assertEquals(er, str, true);
	}

	@Test
	public void testGetInfoById() throws Exception {
		String url = "/api/user/1";
		Mockito.when(repository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(new UserEntity(1L, "hanumareddy", "", "hanuma", "reddy", 23, 20000D)));
		RequestBuilder rb = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		/*
		 * mockMvc.perform(rb).andExpect(status().isOk()).andExpect(jsonPath("id",
		 * is(1))).andExpect(jsonPath("username", is("hanumareddy")))
		 * .andExpect(jsonPath("password", is(""))).andExpect(jsonPath("firstName",
		 * is("hanuma"))).andExpect(jsonPath("lastName", is("reddy")))
		 * .andExpect(jsonPath("age", is(23))).andExpect(jsonPath("salary",
		 * is(20000.0)));
		 */
		MvcResult mr = mockMvc.perform(rb).andReturn();
		String actual = mr.getResponse().getContentAsString();
		String expect = "{\"id\":1,\"username\":\"hanumareddy\",\"password\":\"\",\"firstName\":\"hanuma\",\"lastName\":\"reddy\",\"age\":23,\"salary\":20000.0}";
		JSONAssert.assertEquals(expect, actual, true);
	}

	@Test
	public void testCreateUser() throws Exception {
		String uri = "/api/user";
		UserEntity u = new UserEntity(1L, "srinu", "", "srinu", "srinu", 28, 20800D);
		String inputJson = mapToJson(u);
		Mockito.when(repository.save(new UserEntity(1L, "srinu", "", "srinu", "srinu", 28, 20800D))).thenReturn(new UserEntity(1L, "srinu", "", "srinu", "srinu", 28, 20800D));
		RequestBuilder rb = MockMvcRequestBuilders.post(uri).content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE);
//		mockMvc.perform(rb).andExpect(status().is(200));
		MvcResult mr = mockMvc.perform(rb).andReturn();
		String actual = mr.getResponse().getContentAsString();
		System.out.println("result:"+actual);
		String expected = "{\"id\":1,\"username\":\"srinu\",\"password\":\"\",\"firstName\":\"srinu\",\"lastName\":\"srinu\",\"age\":28,\"salary\":20800.0}";
		JSONAssert.assertEquals(expected, actual, true);

	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
