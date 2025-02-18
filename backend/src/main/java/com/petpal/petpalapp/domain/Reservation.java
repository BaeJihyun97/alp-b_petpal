// package com.petpal.petpalapp.domain;

// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotNull;
// import lombok.Getter;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter
// @Table(name = "reservations")
// public class Reservation {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private Long userId;

//     @Column(nullable = false)
//     private Long petSitterId;

//     @Column(nullable = false)
//     private Long serviceId;

//     @Column(nullable = false)
//     private LocalDateTime startDateTime;

//     @Column(nullable = false)
//     private LocalDateTime endDateTime;

//     @Column(nullable = false)
//     private Integer totalPrice;

//     @Enumerated(EnumType.STRING)
//     private ReservationStatus status;

//     // 예약 상태 enum 결제전, 확인, 취소, 완료
//     public enum ReservationStatus {
//         PENDING, CONFIRMED, CANCELLED, COMPLETED
//     }
// }