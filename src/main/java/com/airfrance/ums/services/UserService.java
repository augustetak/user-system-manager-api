package com.airfrance.ums.services;

import com.airfrance.ums.controllers.UserResponseDto;
import com.airfrance.ums.dto.UserDto;
import com.airfrance.ums.entities.User;
import com.airfrance.ums.exception.BadRequestException;

import java.util.List;
import java.util.Optional;


public interface UserService {
public UserResponseDto checkFields(UserDto userDto, boolean updateOperation) throws BadRequestException;
public UserResponseDto createUser(UserDto userDto, boolean isUpdateOperation) throws BadRequestException;
public User dtoToUser(UserDto userDto, boolean isUpdateOperation);
public Optional<User> getUserById(String userId);
public List<User> getAllUser();
}
