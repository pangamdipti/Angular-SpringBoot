package com.springbootajax.test.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootajax.controller.UserController;
import com.springbootajax.repository.UserRepository;
import com.springbootajax.service.UserService;
import com.springbootajax.model.User;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository repo;

	@MockBean
	private UserService service;

	ObjectMapper objMap = new ObjectMapper();

	public static List<User> setUp() throws ParseException {
		User user = new User();
		// user.setId(1);
		user.setFirstName("Dipti");
		user.setLastName("Pangam");
		user.setEmailId("pangam@gmail.com");
		user.setContact("9969969599");

		User user1 = new User();
		// user1.setId(2);
		user1.setFirstName("Ankit");
		user1.setLastName("Karde");
		user1.setEmailId("karde@gmail.com");
		user1.setContact("9762280007");

		User user2 = new User();
		// user2.setId(3);
		user2.setFirstName("Arpita");
		user2.setLastName("Poojari");
		user2.setEmailId("poojari@gmail.com");
		user2.setContact("9876543217");

		List<User> listUser = new ArrayList<>();
		listUser.add(user);
		listUser.add(user1);
		listUser.add(user2);

		return listUser;
	}

	@Test
	@WithMockUser(username = "user", password = "test123")
	public void saveUserTest() throws Exception {

		Mockito.when(service.createUser(UserControllerTest.setUp().get(0))).thenReturn(1);

		String payload = objMap.writeValueAsString(UserControllerTest.setUp().get(0));
		MvcResult result = mockMvc
				.perform(post("/api/v1/users").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@WithMockUser(username = "user", password = "test123")
	public void getAllUserTest() throws Exception {
		List<User> listUser = UserControllerTest.setUp();
		String response = objMap.writeValueAsString(listUser);
		Mockito.when(service.getAllUsers()).thenReturn(listUser);
		MvcResult result = mockMvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String userRes = result.getResponse().getContentAsString();
		assertEquals(response, userRes);
	}

	@Test
	@WithMockUser(username = "user", password = "test123")
	public void getUserByIdTest() throws Exception {
		String response = objMap.writeValueAsString(UserControllerTest.setUp().get(0));
		Mockito.when(service.findById((long) 1)).thenReturn(UserControllerTest.setUp().get(0));
		MvcResult result = mockMvc.perform(get("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "user", password = "test123")
	public void updateUserTest() throws Exception {
		Mockito.when(service.updateUser((long) 2, UserControllerTest.setUp().get(2)))
				.thenReturn(UserControllerTest.setUp().get(2));

		String payload = objMap.writeValueAsString(UserControllerTest.setUp().get(2));
		MvcResult result = mockMvc
				.perform(put("/api/v1/users/2").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}

	@Test
	@WithMockUser(username = "user", password = "test123")
	public void deleteUserTest() throws Exception {
		MvcResult result = mockMvc.perform(delete("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}

}
