package com.petpal.petpalapp.repository;

import com.petpal.petpalapp.domain.code.CodeDetail;
import com.petpal.petpalapp.domain.code.CodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {
    List<CodeDetail> findByGroupId(String groupId);
    List<CodeDetail> findByGroupIdAndIsActiveTrue(String groupId);
    void deleteByGroupId(String groupId);
} 