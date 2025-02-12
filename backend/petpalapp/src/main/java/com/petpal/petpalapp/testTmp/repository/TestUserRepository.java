package com.petpal.petpalapp.testTmp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petpal.petpalapp.testTmp.domain.TestUser;

@Repository
public interface TestUserRepository extends JpaRepository<TestUser, Long> {
}