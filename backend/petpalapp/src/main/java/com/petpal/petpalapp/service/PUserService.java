package com.petpal.petpalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petpal.petpalapp.common.enums.PState;
import com.petpal.petpalapp.domain.PUser;
import com.petpal.petpalapp.repository.PUserRepository;

@Service
public class PUserService {
    private final PUserRepository pUserRepository;

    @Autowired
    public PUserService(PUserRepository pUserRepository) {
        this.pUserRepository = pUserRepository;
    }

    public PUser createPUser(PUser user) {
        if (pUserRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Exist Email Address");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Empty Password");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Empty Name");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Empty Phone Number");
        }

        // if null value set current time
        user.setUpdatedAt(LocalDateTime.now());
        if (user.getCreatedAt() == null)
            user.setCreatedAt(LocalDateTime.now());
        if (user.getState() == null)
            user.setState(PState.ENABLED);
        if (user.getRegisterDate() == null)
            user.setRegisterDate(LocalDateTime.now());

        return pUserRepository.save(user);
    }

    public Optional<PUser> updatePUser(Long id, PUser user) {

        if (!pUserRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id " + id + "not found");
        }

        PUser existingUser = pUserRepository.findById(id).get();

        if (user.getName() != null || !user.getName().isEmpty()) {
            existingUser.setName(user.getName());
        }

        if (user.getPhoneNumber() != null || !user.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }

        // if null value set current time
        existingUser.setUpdatedAt(LocalDateTime.now());

        return Optional.ofNullable(pUserRepository.save(existingUser));

    }

    public Optional<PUser> updatePasswordPUser(PUser user) {

        if (!pUserRepository.existsById(user.getUserId())) {
            throw new IllegalArgumentException("User with id " + user.getUserId() + "not found");
        }

        PUser existingUser = pUserRepository.findById(user.getUserId()).get();

        if (user.getPassword() != null || !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }

        // if null value set current time
        existingUser.setUpdatedAt(LocalDateTime.now());

        return Optional.ofNullable(pUserRepository.save(existingUser));

    }

    public List<PUser> getAllPUsers() {
        return pUserRepository.findAll();
    }

    public Optional<PUser> getPUserById(Long id) {
        return pUserRepository.findById(id);
    }

    public void deletePUser(Long id) {
        pUserRepository.deleteById(id);
    }

}
