package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petpal.petpalapp.domain.PUser;
import java.util.List;
import java.util.Optional;

public interface PUserRepository extends JpaRepository<PUser, Long> {
    Optional<PUser> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // 그룹 코드와 상세 코드로 사용자 찾기
    List<PUser> findByUserGroupCodeIdAndUserGroupCodeGroupId(
        String userGroupCodeId, 
        String userGroupCodeGroupId
    );
}
