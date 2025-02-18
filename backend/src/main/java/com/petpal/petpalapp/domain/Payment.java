package com.petpal.petpalapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payments")
public class Payment extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String impUid;        // 아임포트 결제 고유번호
    
    @Column(nullable = false, length = 50)
    private String merchantUid;   // 주문번호
    
    @Column(nullable = false)
    private Integer paidAmount;   // 결제금액
    
    @Column(nullable = false, length = 20)
    private String payMethod;     // 결제수단
    
    @Column(nullable = false)
    private LocalDateTime paidAt; // 결제일시
    
    @Column(nullable = false, length = 20)
    private String status;        // 결제상태
    
    @Column(nullable = false, length = 50)
    private String buyerName;     // 구매자 이름
    
    @Column(nullable = false, length = 100)
    private String buyerEmail;    // 구매자 이메일
    
    @Column(nullable = false, length = 20)
    private String buyerTel;      // 구매자 연락처

    @Builder
    public Payment(String impUid, String merchantUid, Integer paidAmount, 
                  String payMethod, LocalDateTime paidAt, String status,
                  String buyerName, String buyerEmail, String buyerTel) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.paidAmount = paidAmount;
        this.payMethod = payMethod;
        this.paidAt = paidAt;
        this.status = status;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
    }
}
