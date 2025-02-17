package com.petpal.petpalapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
// import com.petpal.petpalapp.domain.code.PetTypeCode;
@Entity 
@Table(name = "pet_sitter_service")
@Getter
@Setter
public class PetSitterService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;

    // @Column(name = "service_name")
    // private String serviceName;

    // @ManyToOne
    // @JoinColumn(name = "pet_type_code")
    // private PetTypeCode petTypeCode;    

    @Column(name = "service_fee")
    private String serviceFee;

    // @Column(name = "service_location")
    // private String serviceLocation;
    
}