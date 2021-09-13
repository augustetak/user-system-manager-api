package com.airfrance.ums.controllers;

import com.airfrance.ums.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {

    private String userId;
    private String name;
    private String surname;
    private String email;
    private LocalDateTime birthday;
    private String country;

    public UserDto buildUserDTO(){
        return UserDto.builder().userId(this.userId).name(this.name).surname(this.surname).birthday(this.birthday).country(this.country)
                .email(this.email).build();
    }
}
