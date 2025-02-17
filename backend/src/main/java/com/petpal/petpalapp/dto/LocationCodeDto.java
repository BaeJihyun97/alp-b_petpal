package com.petpal.petpalapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationCodeDto {
    private String locationCode;
    private String locationName;
    private String parent;
    private String description;
} 