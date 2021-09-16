package com.airfrance.ums.controllers;


import com.airfrance.ums.dto.UserResultCode;
import com.airfrance.ums.entities.User;
import com.airfrance.ums.exception.BadRequestException;
import com.airfrance.ums.services.UserService;


import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/user/add")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequest userRequest) throws BadRequestException {
        long start  = new Date().getTime();
        logger.info("Start create new user {}",userRequest);
        UserResponseDto userResponseDto = userService.createUser(userRequest.buildUserDTO(), false);
        HttpStatus status = UserResultCode.SUCCESS.getStatusCode().equals(userResponseDto.getCode()) ? HttpStatus.OK: HttpStatus.BAD_REQUEST;
        ResponseEntity<UserResponseDto> userResponseEntity = new ResponseEntity<>(userResponseDto, status);
        logger.info("End create new user computation time: {}ms",new Date().getTime() - start);

        return userResponseEntity;
    }

    @PatchMapping("/user/update")
    public ResponseEntity<UserResponseDto> patchUser(@RequestBody UserRequest userRequest) throws BadRequestException {
        long start  = new Date().getTime();
        logger.info("Start update user {}",userRequest);
        UserResponseDto userResponseDto = userService.createUser(userRequest.buildUserDTO(), true);
        HttpStatus status = UserResultCode.SUCCESS.getStatusCode().equals(userResponseDto.getCode()) ? HttpStatus.OK: HttpStatus.BAD_REQUEST;
        ResponseEntity<UserResponseDto> userResponseEntity = new ResponseEntity<>(userResponseDto, status);
        logger.info("End update user computation time: {}ms",new Date().getTime() - start);
        return userResponseEntity;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        long start  = new Date().getTime();
        Optional<User> user = userService.getUserById(userId);
        ResponseEntity<User> userResponseEntity = user.map(usr -> new ResponseEntity<User>(usr, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
        logger.info("End get User  computation time: {}ms",new Date().getTime() - start);
        return userResponseEntity;
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        long start  = new Date().getTime();
        List<User> allUser = userService.getAllUser();
        logger.info("End get all user  computation time: {}ms",new Date().getTime() - start);
        return allUser;
    }
}
