package com.airfrance.ums.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.airfrance.ums.entities.User;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<User, String>{
 public  User findUserByEmail(String email);
}
