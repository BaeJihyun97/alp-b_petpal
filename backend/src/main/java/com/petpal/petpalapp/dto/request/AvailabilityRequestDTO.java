package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityRequestDTO {
    private String dayOfWeek;
    private String time;
} 