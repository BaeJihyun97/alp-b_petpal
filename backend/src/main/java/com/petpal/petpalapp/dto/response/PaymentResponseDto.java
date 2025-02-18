package com.petpal.petpalapp.dto.response;

import lombok.Getter;
import java.time.LocalDateTime;

import com.petpal.petpalapp.domain.Payment;

@Getter
public class PaymentResponseDto {
    private Long id;
    private String impUid;
    private String merchantUid;
    private Integer paidAmount;
    private String payMethod;
    private LocalDateTime paidAt;
    private String status;
    private String buyerName;
    private String buyerEmail;
    private String buyerTel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaymentResponseDto(Payment payment) {
        this.id = payment.getId();
        this.impUid = payment.getImpUid();
        this.merchantUid = payment.getMerchantUid();
        this.paidAmount = payment.getPaidAmount();
        this.payMethod = payment.getPayMethod();
        this.paidAt = payment.getPaidAt();
        this.status = payment.getStatus();
        this.buyerName = payment.getBuyerName();
        this.buyerEmail = payment.getBuyerEmail();
        this.buyerTel = payment.getBuyerTel();
        this.createdAt = payment.getCreatedAt();
        this.updatedAt = payment.getUpdatedAt();
    }
}
