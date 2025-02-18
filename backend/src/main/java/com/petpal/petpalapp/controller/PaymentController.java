package com.petpal.petpalapp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity; 

import lombok.RequiredArgsConstructor;

import com.petpal.petpalapp.service.PaymentService;
import com.petpal.petpalapp.domain.Payment;
import com.petpal.petpalapp.dto.request.PaymentRequestDto;
import com.petpal.petpalapp.dto.response.PaymentResponseDto;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> savePayment(@RequestBody PaymentRequestDto requestDto) {
        Payment payment = paymentService.savePayment(requestDto);
        return ResponseEntity.ok(new PaymentResponseDto(payment));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        List<Payment> payments = paymentService.findAllPayments();
        List<PaymentResponseDto> response = payments.stream()
                .map(PaymentResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/imp/{impUid}")
    public ResponseEntity<PaymentResponseDto> getPaymentByImpUid(@PathVariable String impUid) {
        Payment payment = paymentService.findByImpUid(impUid);
        return ResponseEntity.ok(new PaymentResponseDto(payment));
    }

    @GetMapping("/merchant/{merchantUid}")
    public ResponseEntity<PaymentResponseDto> getPaymentByMerchantUid(@PathVariable String merchantUid) {
        Payment payment = paymentService.findByMerchantUid(merchantUid);
        return ResponseEntity.ok(new PaymentResponseDto(payment));
    }
}
