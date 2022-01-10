package com.mainstream.userManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mainstream.userManagement.Controller.UserController;
import com.mainstream.userManagement.Model.User;
import com.mainstream.userManagement.Service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementApplicationTests{

 private MockMvc mockMvc;

 ObjectMapper objectMapper = new ObjectMapper();
 ObjectWriter objectWriter = objectMapper.writer();

 @Mock
 private UserService userService;

 @InjectMocks
 private UserController userController;

 User user1 = new User(1l,"Morris","Mokwebo","0735712349","morris@gmail.com");
 User user2 = new User(2l,"Roland","Timms","0736013334","roland@gmail.com");
 User user3 = new User(3l,"Jack","Smith","0640970994","jack@gmail.com");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getAllUserRecords() throws Exception{
        List<User> allUserRecords = new ArrayList<>(Arrays.asList(user1,user2,user3));

        Mockito.when(userService.retrieveAllUsers()).thenReturn(allUserRecords);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[2].firstName",is("Jack")));
    }

    @Test
    public void getUserById() throws Exception{
        List<User> userRecord = new ArrayList<>(Arrays.asList(user1,user2,user3));

        Mockito.when(userService.retrieveUserById(user1.getId())).thenReturn(user1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.firstName",is("Morris")));
    }

    @Test
    public void createUserRecord() throws Exception{
        User newUserRecord = User.builder()
                .id(4l)
                .firstName("Sam")
                .lastName("Dean")
                .email("sam@gmail.com").build();

        Mockito.when(userService.saveUser(newUserRecord)).thenReturn(newUserRecord);

        String content = objectWriter.writeValueAsString(newUserRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.firstName",is("Sam")));

    }

    @Test
    public void updateUserRecord() throws Exception{
        User updatedRecord = User.builder()
                .id(1L)
                .firstName("Montana")
                .lastName("Mokwebo")
                .email("montana@gmail.com")
                .cellNumber("0796414223")
                .build();

        Mockito.when(userService.updateUserById(user1.getId(),updatedRecord)).
                thenReturn(updatedRecord);

        String content = objectWriter.writeValueAsString(updatedRecord);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/users/updateUser/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.firstName",is("Montana")));
    }

    @Test
    public void deleteUserRecord() throws Exception {
     Mockito.when(userService.deleteUser(user3.getId())).thenReturn(ResponseEntity.of(java.util.Optional.of(user3)));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/users/3")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

}
