package com.mainstream.userManagement.Controller;

import com.mainstream.userManagement.Model.User;
import com.mainstream.userManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * User controller contains all the user endpoints
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Retrieve all users endpoint
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return userService.retrieveAllUsers();
    }

    //Retrieve user by id endpoint
    @GetMapping("/{id}")
    public User getUser(@PathVariable long id){
        return userService.retrieveUserById(id);
    }

    //Insert new user end point
    @PostMapping("/newUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser(@RequestBody User newUser){
        return userService.saveUser(newUser);
    }

    //Update existing user by id endpoint
    @PutMapping("/updateUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateExistingUser(@PathVariable long id, @RequestBody User existingUser){
        return userService.updateUserById(id,existingUser);
    }

    //Delete existing user by id endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteExistingUser(@PathVariable long id){
        return userService.deleteUser(id);
    }


}
