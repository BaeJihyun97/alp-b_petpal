package com.petpal.petpalapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.EnumMap;
import java.util.ArrayList;
import java.util.Collections;

import com.petpal.petpalapp.domain.PetsitterAvailability;
import com.petpal.petpalapp.repository.PetsitterAvailabilityRepository;
import com.petpal.petpalapp.repository.PetSitterRepository;
import jakarta.persistence.EntityNotFoundException;
import com.petpal.petpalapp.domain.PetSitter;
import com.petpal.petpalapp.dto.request.AvailabilityRequestDTO;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetsitterAvailabilityService {

    private final PetsitterAvailabilityRepository availabilityRepository;
    private final PetSitterRepository petsitterRepository;

    @Transactional
    public void createAvailability(Long petsitterId, List<List<Boolean>> weeklyTimeSlots) {
        if (weeklyTimeSlots.size() != 7) {
            throw new IllegalArgumentException("일주일치 시간표가 필요합니다");
        }

        PetSitter petsitter = petsitterRepository.findById(petsitterId)
            .orElseThrow(() -> new EntityNotFoundException("펫시터를 찾을 수 없습니다."));
        
        PetsitterAvailability availability = new PetsitterAvailability(petsitter.getPetSitterId());

        // 월요일부터 일요일까지 순서대로 설정
        DayOfWeek[] days = DayOfWeek.values();
        for (int i = 0; i < 7; i++) {
            availability.setDayAvailability(days[i], weeklyTimeSlots.get(i));
        }
            
        availabilityRepository.save(availability);
    }

    @Transactional
    public void updateDayAvailability(Long petsitterId, DayOfWeek day, List<Boolean> timeSlots) {
        PetsitterAvailability availability = availabilityRepository.findByPetsitterId(petsitterId)
            .orElseThrow(() -> new EntityNotFoundException("가능 시간 정보를 찾을 수 없습니다."));
        
        availability.setDayAvailability(day, timeSlots);
    }

    public List<Boolean> getDayAvailability(Long petsitterId, DayOfWeek day) {
        PetsitterAvailability availability = availabilityRepository.findByPetsitterId(petsitterId)
            .orElseThrow(() -> new EntityNotFoundException("가능 시간 정보를 찾을 수 없습니다."));
        
        return availability.getDayAvailability(day);
    }

    public Map<DayOfWeek, List<Boolean>> getWeekAvailability(Long petsitterId) {
        PetsitterAvailability availability = availabilityRepository.findByPetsitterId(petsitterId)
            .orElseThrow(() -> new EntityNotFoundException("가능 시간 정보를 찾을 수 없습니다."));

        Map<DayOfWeek, List<Boolean>> weekAvailability = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            weekAvailability.put(day, availability.getDayAvailability(day));
        }
        return weekAvailability;
    }

    @Transactional
    public void initializeAvailability(Long petsitterId) {
        PetsitterAvailability availability = new PetsitterAvailability(petsitterId);
        
        // 모든 요일에 대해 48개의 타임슬롯을 false로 초기화
        for (DayOfWeek day : DayOfWeek.values()) {
            List<Boolean> daySlots = new ArrayList<>(Collections.nCopies(48, false));
            availability.setDayAvailability(day, daySlots);
        }
        
        availabilityRepository.save(availability);
    }

    @Transactional
    public void updateAvailability(Long petsitterId, List<AvailabilityRequestDTO> availabilityRequests) {
        PetsitterAvailability availability = availabilityRepository.findByPetsitterId(petsitterId)
            .orElseThrow(() -> new EntityNotFoundException("가능 시간 정보를 찾을 수 없습니다."));
        
        // 모든 시간을 false로 초기화
        for (DayOfWeek day : DayOfWeek.values()) {
            List<Boolean> daySlots = new ArrayList<>(Collections.nCopies(48, false));
            availability.setDayAvailability(day, daySlots);
        }
        
        // 요청된 시간만 true로 설정
        for (AvailabilityRequestDTO request : availabilityRequests) {
            DayOfWeek day = DayOfWeek.valueOf(request.getDayOfWeek().toUpperCase());
            int slotIndex = convertTimeToSlotIndex(request.getTime());
            List<Boolean> daySlots = availability.getDayAvailability(day);
            daySlots.set(slotIndex, true);
            availability.setDayAvailability(day, daySlots);
        }
        
        availabilityRepository.save(availability);
    }

    private int convertTimeToSlotIndex(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour * 2 + (minute == 30 ? 1 : 0);
    }
} 