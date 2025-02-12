package com.petpal.petpalapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet_type_code")
public class PetTypeCode {

    @Id
    @Column(name = "pet_type_code", nullable = false, length = 20)
    private String petTypeCode;

    @Column(name = "pet_type_name", nullable = false, length = 20)
    private String petTypeName;

    @ManyToOne
    @JoinColumn(name = "pet_type_id_parent", nullable = true)
    private PetTypeCode parent;

}
