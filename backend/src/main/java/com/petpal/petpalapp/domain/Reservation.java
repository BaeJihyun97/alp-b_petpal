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
// public class Reservation {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long reservationId;

//     @Column(name = "pet_sitter_id")
//     private Long petSitterId;

//     @Column(name = "buyer_id")
//     private Long buyerId;

//     @Column(name = "service_id")
//     private Long serviceId;

//     @Column(name = "payment_id")
//     private String paymentId;

//     @Enumerated(EnumType.STRING)
//     private ReservationStatus status;

//     @Builder
//     public Reservation(Long petSitterId, Long buyerId, Long serviceId, String paymentId) {
//         this.petSitterId = petSitterId;
//         this.buyerId = buyerId;
//         this.serviceId = serviceId;
//         this.paymentId = paymentId;
//         this.status = ReservationStatus.PENDING;
//     }

//     public enum ReservationStatus {
//         PENDING,    // 대기중
//         CONFIRMED,  // 확정됨a
//         COMPLETED,  // 완료됨
//         CANCELLED   // 취소됨
//     }
// }

