package com.petpal.petpalapp.domain.code;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "code_detail")
@Getter
@Setter
@IdClass(CodeDetailId.class)
public class CodeDetail {
    @Id
    @Column(name = "code_id", length = 50)
    private String codeId;

    @Id
    @Column(name = "group_id", length = 50)
    private String groupId;

    @Column(name = "code_name", nullable = false, length = 100)
    private String codeName;

    @Column(name = "code_value", length = 50)
    private String codeValue;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
} 