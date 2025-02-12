package com.petpal.petpalapp.testTmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petpal.petpalapp.testTmp.domain.TestUser;
import com.petpal.petpalapp.testTmp.repository.TestUserRepository;

@Service
public class TestUserService {

    private final TestUserRepository testUserRepository;

    @Autowired
    public TestUserService(TestUserRepository testUserRepository) {
        this.testUserRepository = testUserRepository;
    }

    public TestUser saveUser(TestUser user) {
        if (user.getCreateDate() == null)
            user.setCreateDate();
        return testUserRepository.save(user);
    }

    public List<TestUser> getAllUsers() {
        return testUserRepository.findAll();
    }

    public Optional<TestUser> getUserById(Long id) {
        return testUserRepository.findById(id);
    }

    public void deleteUser(Long id) {
        testUserRepository.deleteById(id);
    }

}
