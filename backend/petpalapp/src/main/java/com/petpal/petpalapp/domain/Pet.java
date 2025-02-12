package com.petpal.petpalapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pet")
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 엔티티의 PK 참조 (FK)
    private PUser user;

    @NotNull
    @Column(nullable = false)
    private String petName;

    @Min(0) // 0 이상만 허용
    @Column(nullable = false)
    private int petAge;

    @OneToOne
    @JoinColumn(name = "pet_type_code", nullable = false)
    private PetTypeCode petTypeCode;

}
