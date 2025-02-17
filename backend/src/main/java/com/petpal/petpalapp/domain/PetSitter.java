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
import lombok.Getter;
import lombok.Setter;
// import com.petpal.petpalapp.domain.code.LocationCode;
import java.time.LocalDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pet_sitter")
public class PetSitter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_sitter_id", nullable = false)
    private Long petSitterId; // PK

    @OneToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PUser user;

    // @ManyToOne
    // @JoinColumn(name = "location_code", nullable = true)
    // private LocationCode locationCode; // location 테이블 PK 참조조

    @Column(name = "location_code_id", length = 50)
    private String locationCodeId;

    @Column(name = "location_code_group_id", length = 50)
    private String locationCodeGroupId;

    @Column(name = "nickname", length = 50, nullable = false)
    private String nickname;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "introduction", columnDefinition = "TEXT", nullable = false)
    private String introduction; // 소개글 (TEXT 타입)

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PState state; // Enum 필드 (ENABLED, DISABLED)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
