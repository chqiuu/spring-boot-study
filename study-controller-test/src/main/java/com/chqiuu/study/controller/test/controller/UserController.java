package com.chqiuu.study.controller.test.controller;

import com.chqiuu.study.controller.test.entity.UserInfoEntity;
import com.chqiuu.study.controller.test.model.Role;
import com.chqiuu.study.controller.test.model.User;
import com.chqiuu.study.controller.test.service.UserService;
import com.chqiuu.study.controller.test.vo.UserInputVO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chqiu
 */
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/users")
    public List<User> findAllStudents() {
        return userService.findAllUsers();
    }

    @GetMapping(value = "/users/{id}/roles")
    public List<Role> findUserRoles(@PathVariable(value = "id") String id) {
        return userService.findUserAllRoles(id);
    }

    @PostMapping(value = "/users/{id}")
    public ResponseEntity<Object> setUserRole(@PathVariable(value = "id") String id, @RequestBody Role role) {
        userService.addUserRole(id, role);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
}
