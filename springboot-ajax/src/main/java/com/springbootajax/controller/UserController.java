package com.springbootajax.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springbootajax.exception.ResourceNotFoundException;
import com.springbootajax.model.User;
import com.springbootajax.security.AuthenticationBean;
import com.springbootajax.service.UserService;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	UserService userService;

	//get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
    	return userService.getAllUsers();
        //return userRepository.findAll();
    }
    
    

    //get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
        throws ResourceNotFoundException {
    	User user = userService.findById(userId);
    	
    	//User user = userRepository.findById(userId)
         // .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }
    
  //search by value
    @GetMapping("/users/search/{keyword}")
	public List<User> searchUser(@PathVariable(value = "keyword") String keyword) 
    throws ResourceNotFoundException{
    	System.out.println(keyword);
		//logger.info("Searching user...");
		List<User> listUsers = userService.getUserByValue(keyword);
		System.out.println(listUsers);
		return listUsers;
	}
    
    //save user
    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user) {
    	userService.createUser(user);
        //return userRepository.save(user);
    }

    //update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
         @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
    	
    	final User updatedUser = userService.updateUser(userId, userDetails);
		/*
		 * User user = userRepository.findById(userId) .orElseThrow(() -> new
		 * ResourceNotFoundException("User not found for this id :: " + userId));
		 * 
		 * user.setEmailId(userDetails.getEmailId());
		 * user.setLastName(userDetails.getLastName());
		 * user.setFirstName(userDetails.getFirstName());
		 * user.setContact(userDetails.getContact()); final User updatedUser =
		 * userRepository.save(user);
		 */
        return ResponseEntity.ok(updatedUser);
    } 

    //delete user
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
         throws ResourceNotFoundException {
    	User user = userService.findById(userId);
    	//User user = userRepository.findById(userId)
      // .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

    	userService.delete(user);
        //userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
    
    
    @GetMapping(path="/basicauth")
    public AuthenticationBean basicauth() {
    	return new AuthenticationBean("You are authenticated");
    }
}
