package com.chqiuu.study.controller.test.model;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private String id;

    private String name;

    private String alias;

    private List<Role> roles;
}