package com.petpal.petpalapp.repository;

import com.petpal.petpalapp.domain.code.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CodeGroupRepository extends JpaRepository<CodeGroup, String> {
    List<CodeGroup> findByParentGroupIdIsNull();
    List<CodeGroup> findByParentGroupId(String parentGroupId);
} 