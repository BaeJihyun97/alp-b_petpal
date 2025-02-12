package com.petpal.petpalapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.service.PUserService;
import com.petpal.petpalapp.testTmp.domain.TestUser;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "유저 API", description = "유저 컨트롤러에 대한 설명입니다.")
public class PUserController {
    private final PUserService pUserService;

    @Autowired
    public PUserController(PUserService pUserService) {
        this.pUserService = pUserService;
    }

    // C
    @PostMapping
    public ResponseEntity<PUser> createPUser(@RequestBody PUser user) {
        PUser createdUser = pUserService.createPUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // R list
    @GetMapping
    public ResponseEntity<List<PUser>> getAllPUsers() {
        List<PUser> users = pUserService.getAllPUsers();
        return ResponseEntity.ok(users);
    }

    // R
    @GetMapping("/{id}")
    public ResponseEntity<PUser> getPUserById(@PathVariable("id") Long id) {
        Optional<PUser> user = pUserService.getPUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // U
    @PutMapping("/{id}")
    public ResponseEntity<PUser> updatePUser(@PathVariable("id") Long id, @RequestBody PUser user) {
        Optional<PUser> updatedUser = pUserService.updatePUser(id, user);
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    // D
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePUser(@PathVariable("id") Long id) {
        pUserService.deletePUser(id);
        return ResponseEntity.noContent().build();
    }

}
