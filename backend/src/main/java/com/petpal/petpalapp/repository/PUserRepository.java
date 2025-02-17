package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petpal.petpalapp.domain.PUser;
import java.util.Optional;

public interface PUserRepository extends JpaRepository<PUser, Long> {
    Optional<PUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
