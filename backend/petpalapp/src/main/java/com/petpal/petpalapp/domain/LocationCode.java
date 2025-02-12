package com.petpal.petpalapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "location_code")
public class LocationCode {
    @Id
    @Column(name = "location_code", nullable = false, length = 20)
    private String locationCode;

    @Column(name = "location_name", nullable = false, length = 20)
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "location_code_parent", nullable = true)
    private LocationCode parent;
}
