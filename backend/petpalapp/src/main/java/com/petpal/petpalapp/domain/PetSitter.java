package com.petpal.petpalapp.domain;

import com.petpal.petpalapp.common.enums.PState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "pet_sitter")
public class PetSitter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_sitter_id", nullable = false)
    private Long petSitterId; // PK

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private PUser user; // User 테이블의 PK 참조

    @ManyToOne
    @JoinColumn(name = "location_code", nullable = true)
    private LocationCode locationCode; // location 테이블 PK 참조조

    @Column(name = "introduction", columnDefinition = "TEXT", nullable = false)
    private String introduction; // 소개글 (TEXT 타입)

    @Column(name = "experience", columnDefinition = "TEXT", nullable = false)
    private String experience; // 경험 정리서 (TEXT 타입)

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PState state; // Enum 필드 (ENABLED, DISABLED)

}
