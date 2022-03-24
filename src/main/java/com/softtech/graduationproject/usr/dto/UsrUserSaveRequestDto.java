package com.softtech.graduationproject.usr.dto;

import lombok.Data;

@Data
public class UsrUserSaveRequestDto {

    private String username;
    private String password;
    private String name;
    private String surname;
}
