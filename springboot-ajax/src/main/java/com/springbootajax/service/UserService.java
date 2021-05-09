package com.springbootajax.service;

import java.util.List;

import com.springbootajax.model.User;

public interface UserService {
	
	public Object createUser(User user);

	public List<User> getAllUsers();

	public User updateUser(Long userId, User userDetails);

	public User findById(Long userId);

	public void delete(User user);

	public List<User> getUserByValue(String keyword);
}
