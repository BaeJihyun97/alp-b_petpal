package com.petpal.petpalapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.petpal.petpalapp.domain.Payment;
import com.petpal.petpalapp.repository.PaymentRepository;
import com.petpal.petpalapp.dto.request.PaymentRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment savePayment(PaymentRequestDto requestDto) {
        return paymentRepository.save(requestDto.toEntity());
    }

    public Payment findByImpUid(String impUid) {
        return paymentRepository.findByImpUid(impUid)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
    }

    public Payment findByMerchantUid(String merchantUid) {
        return paymentRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
    }

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }
}
