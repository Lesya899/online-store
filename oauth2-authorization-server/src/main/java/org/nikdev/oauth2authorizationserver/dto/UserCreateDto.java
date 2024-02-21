package org.nikdev.oauth2authorizationserver.dto;


import lombok.Data;

@Data
public class UserCreateDto {

    private String userName;
    private String password;
    private String roleName;
    private String email;

}
