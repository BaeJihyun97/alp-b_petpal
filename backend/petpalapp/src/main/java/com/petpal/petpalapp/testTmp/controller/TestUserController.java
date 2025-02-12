package com.petpal.petpalapp.testTmp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalapp.testTmp.domain.TestUser;
import com.petpal.petpalapp.testTmp.service.TestUserService;

@RestController
@RequestMapping("/testusers")
public class TestUserController {
    private final TestUserService testUserService;

    @Autowired
    public TestUserController(TestUserService testUserService) {
        this.testUserService = testUserService;
    }

    @PostMapping
    public ResponseEntity<TestUser> createUser(@RequestBody TestUser user) {
        TestUser createdUser = testUserService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<TestUser>> getAllUsers() {
        List<TestUser> users = testUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestUser> getUserById(@PathVariable Long id) {
        Optional<TestUser> user = testUserService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        testUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
