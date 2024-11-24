package com.example.application.paramas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityUpdateParams {

    private String name;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String type;
}
