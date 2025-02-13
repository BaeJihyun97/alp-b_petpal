package com.petpal.petpalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petpal.petpalapp.domain.UserGroupCode;

public interface UserGroupCodeRepository extends JpaRepository<UserGroupCode, String> {
    // 추가적인 쿼리 메서드가 필요하면 여기에 정의할 수 있습니다.
}