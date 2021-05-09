 package com.springbootajax.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootajax.exception.ResourceNotFoundException;
import com.springbootajax.model.User;
import com.springbootajax.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Object createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User updateUser(Long userId, User userDetails) {
		 User user = userRepository.findById(userId)
		        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

		    	user.setEmailId(userDetails.getEmailId());
		    	user.setLastName(userDetails.getLastName());  
		        user.setFirstName(userDetails.getFirstName());
		        user.setContact(userDetails.getContact());
		        final User updatedUser = userRepository.save(user);
		        return updatedUser;
	}

	@Override
	public User findById(Long userId) {
		User user = userRepository.findById(userId)
         .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
      return user;
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
		
	}


	@Override
	public List<User> getUserByValue(String keyword) {
		if(keyword !=null) {
			return userRepository.search(keyword);
		} else {
			return userRepository.findAll();
		}
				
	}

}
