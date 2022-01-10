package com.mainstream.userManagement.Service;


import com.mainstream.userManagement.Exception.ResourceNotFoundException;
import com.mainstream.userManagement.Model.User;
import com.mainstream.userManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * User service contains user business logic
 */

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    //Retrieve all users method
    public List<User> retrieveAllUsers(){
        return  userRepository.findAll();
    }

    //Retrieve user by id method
    public User retrieveUserById(long id){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw new ResourceNotFoundException("User with id: "+id+" not found");
        }

       User getSpecifiedUser = userOptional.get();
        return  getSpecifiedUser;

    }

    //Insert a user method
    public User saveUser(User newUser){
        return userRepository.save(newUser);
    }

    //update user by id method
    public User updateUserById(long id, User user){
        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){
            throw new ResourceNotFoundException("User with id: "+id+" not found");
        }

        User userTobeUpdated = optionalUser.get();
        userTobeUpdated.setFirstName(user.getFirstName());
        userTobeUpdated.setLastName(user.getLastName());
        userTobeUpdated.setCellNumber(user.getCellNumber());
        userTobeUpdated.setEmail(user.getEmail());

        this.userRepository.save(userTobeUpdated);
        return  userTobeUpdated;

    }

    //delete user by id method
    public ResponseEntity<User> deleteUser(long id){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw  new ResourceNotFoundException("User with id: "+id+" not found");
        }

        User userTobeDeleted = userOptional.get();
        this.userRepository.delete(userTobeDeleted);

        return ResponseEntity.ok().build();

    }


}
