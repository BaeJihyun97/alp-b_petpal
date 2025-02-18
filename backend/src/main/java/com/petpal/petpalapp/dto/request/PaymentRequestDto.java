package com.petpal.petpalapp.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;

import com.petpal.petpalapp.domain.Payment;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    private String impUid;
    private String merchantUid;
    private Integer paidAmount;
    private String payMethod;
    private Long paidAt;        // Unix timestamp로 받음
    private String status;
    private String buyerName;
    private String buyerEmail;
    private String buyerTel;

    public Payment toEntity() {
        return Payment.builder()
                .impUid(impUid)
                .merchantUid(merchantUid)
                .paidAmount(paidAmount)
                .payMethod(payMethod)
                .paidAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(paidAt), ZoneId.systemDefault()))
                .status(status)
                .buyerName(buyerName)
                .buyerEmail(buyerEmail)
                .buyerTel(buyerTel)
                .build();
    }
}
