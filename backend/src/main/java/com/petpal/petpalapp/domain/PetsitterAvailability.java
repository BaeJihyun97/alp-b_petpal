package com.petpal.petpalapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import jakarta.persistence.Id;  
import jakarta.persistence.Column;
import java.time.DayOfWeek;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "petsitter_availability")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetsitterAvailability {

    @Id
    @Column(name = "petsitter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long petsitterId;

    @Column(name = "monday", nullable = false)
    private Long monday = 0L;    // 48비트 사용

    @Column(name = "tuesday", nullable = false)
    private Long tuesday = 0L;   // 48비트 사용

    @Column(name = "wednesday", nullable = false)
    private Long wednesday = 0L; // 48비트 사용

    @Column(name = "thursday", nullable = false)
    private Long thursday = 0L;  // 48비트 사용

    @Column(name = "friday", nullable = false)
    private Long friday = 0L;    // 48비트 사용

    @Column(name = "saturday", nullable = false)
    private Long saturday = 0L;  // 48비트 사용

    @Column(name = "sunday", nullable = false)
    private Long sunday = 0L;    // 48비트 사용

    public PetsitterAvailability(Long petsitterId) {
        this.petsitterId = petsitterId;
        this.monday = 0L;
        this.tuesday = 0L;
        this.wednesday = 0L;
        this.thursday = 0L;
        this.friday = 0L;
        this.saturday = 0L;
        this.sunday = 0L;
    }

    // 특정 요일의 전체 시간대 가능 여부 설정
    public void setDayAvailability(DayOfWeek day, List<Boolean> timeSlots) {
        if (timeSlots.size() != 48) {
            throw new IllegalArgumentException("시간대는 정확히 48개여야 합니다");
        }

        Long dayValue = 0L;
        for (int i = 0; i < 48; i++) {
            if (timeSlots.get(i)) {
                dayValue |= (1L << i);
            }
        }

        switch (day) {
            case MONDAY -> monday = dayValue;
            case TUESDAY -> tuesday = dayValue;
            case WEDNESDAY -> wednesday = dayValue;
            case THURSDAY -> thursday = dayValue;
            case FRIDAY -> friday = dayValue;
            case SATURDAY -> saturday = dayValue;
            case SUNDAY -> sunday = dayValue;
        }
    }

    // 특정 요일의 전체 시간대 가능 여부 조회
    public List<Boolean> getDayAvailability(DayOfWeek day) {
        Long dayValue = switch (day) {
            case MONDAY -> monday;
            case TUESDAY -> tuesday;
            case WEDNESDAY -> wednesday;
            case THURSDAY -> thursday;
            case FRIDAY -> friday;
            case SATURDAY -> saturday;
            case SUNDAY -> sunday;
        };

        List<Boolean> availability = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            availability.add((dayValue & (1L << i)) != 0);
        }
        return availability;
    }
} 