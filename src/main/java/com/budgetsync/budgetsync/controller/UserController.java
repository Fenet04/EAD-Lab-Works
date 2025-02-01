package com.budgetsync.budgetsync.controller;

import org.springframework.http.ResponseEntity;
import com.budgetsync.budgetsync.entity.User;
import com.budgetsync.budgetsync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") 
public class UserController {
	
	@Autowired
    private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
    	userService.registerUser(user);
    	return ResponseEntity.ok("User registered successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
