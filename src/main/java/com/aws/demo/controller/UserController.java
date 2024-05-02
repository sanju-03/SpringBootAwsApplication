package com.aws.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aws.demo.entity.User;
import com.aws.demo.exception.ResourceNotFoundException;
import com.aws.demo.repo.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/getAll")
	public List<User> getAllUsers() {
        return this.userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value="id") long userId) {
		return this.userRepository.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User not found with id :"+userId));
	}
	
	
	@PostMapping("/create")
	public User createUser(@RequestBody User user)
	{
		return this.userRepository.save(user);
	}
	
	
	@PutMapping("/update/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
		
		User existingUser=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User Not Found with id :" +userId));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return this.userRepository.save(existingUser);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long userId){
		User existingUser=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User Not Found with id :" +userId));
		this.userRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}

}
