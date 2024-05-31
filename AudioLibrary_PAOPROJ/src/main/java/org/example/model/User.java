package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

public class User {
    @Getter
    @Setter

    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String role;






}


