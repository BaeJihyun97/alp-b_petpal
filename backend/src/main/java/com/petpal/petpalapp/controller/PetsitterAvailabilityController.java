package com.petpal.petpalapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petpal.petpalapp.service.PetsitterAvailabilityService;
import com.petpal.petpalapp.dto.request.AvailabilityRequestDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/petsitters/{petsitterId}/availability")
@RequiredArgsConstructor
public class PetsitterAvailabilityController {

    private final PetsitterAvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<Void> createAvailability(
            @PathVariable Long petsitterId,
            @RequestBody List<List<Boolean>> weeklyTimeSlots) {
        availabilityService.createAvailability(petsitterId, weeklyTimeSlots);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{day}")
    public ResponseEntity<Void> updateDayAvailability(
            @PathVariable Long petsitterId,
            @PathVariable DayOfWeek day,
            @RequestBody List<Boolean> timeSlots) {
        availabilityService.updateDayAvailability(petsitterId, day, timeSlots);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{day}")
    public ResponseEntity<List<Boolean>> getDayAvailability(
            @PathVariable Long petsitterId,
            @PathVariable DayOfWeek day) {
        List<Boolean> availability = availabilityService.getDayAvailability(petsitterId, day);
        return ResponseEntity.ok(availability);
    }

    @GetMapping
    public ResponseEntity<Map<DayOfWeek, List<Boolean>>> getWeekAvailability(
            @PathVariable Long petsitterId) {
        Map<DayOfWeek, List<Boolean>> availability = availabilityService.getWeekAvailability(petsitterId);
        return ResponseEntity.ok(availability);
    }

    @PutMapping
    public ResponseEntity<Void> updateAvailability(
            @PathVariable Long petsitterId,
            @RequestBody List<AvailabilityRequestDTO> availabilityRequests) {
        availabilityService.updateAvailability(petsitterId, availabilityRequests);
        return ResponseEntity.ok().build();
    }
} 