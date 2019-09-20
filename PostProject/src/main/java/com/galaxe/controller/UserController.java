package com.galaxe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.galaxe.entity.UserEntity;
import com.galaxe.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/user/getAll")
	public List<UserEntity> getAllUsers() {
		System.out.println("Get all User...");

		List<UserEntity> list = new ArrayList<>();
		Iterable<UserEntity> customers = userRepository.findAll();

		customers.forEach(list::add);
		return list;
	}

	@PostMapping("/user")
	public UserEntity addUser(@Valid @RequestBody UserEntity user) {
		System.out.println("Create User: " + user.getUsername() + "...");

		return userRepository.save(user);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserEntity> getUser(@PathVariable("id") Long id) {
		System.out.println("Get User by id...");

		Optional<UserEntity> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<UserEntity> updateBook(@PathVariable("id") Long id, @Valid @RequestBody UserEntity user) {
		System.out.println("Update User with ID = " + id + "...");

		Optional<UserEntity> userData = userRepository.findById(id);
		if (userData.isPresent()) {
			UserEntity savedUser = userData.get();
			savedUser.setAge(user.getAge());
			savedUser.setFirstName(user.getFirstName());
			savedUser.setLastName(user.getLastName());
			savedUser.setPassword(user.getPassword());
			savedUser.setSalary(user.getSalary());
			UserEntity updatedUser = userRepository.save(savedUser);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
		System.out.println("Delete User with ID = " + id + "...");

		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("Book has been deleted!", HttpStatus.OK);
	}

}