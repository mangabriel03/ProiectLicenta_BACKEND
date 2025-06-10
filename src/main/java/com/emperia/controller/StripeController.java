package com.emperia.controller;

import com.emperia.dto.ConfirmPaymentDto;
import com.emperia.dto.CreatePaymentDto;
import com.emperia.dto.CreatePaymentResponseDto;
import com.emperia.service.BillService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StripeController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.api.webhook.secret}")
    private String stripeWebhookSecret;

    @Autowired
    private BillService billService;

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponseDto createPaymentIntent(@Valid @RequestBody CreatePaymentDto createPayment)throws StripeException {

        Stripe.apiKey = stripeApiKey;

        PaymentIntentCreateParams createParams = new
                PaymentIntentCreateParams.Builder()
                .setAmount(calculateOrderAmount(createPayment.getBillIds()))
                .setCurrency("ron")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
        return new CreatePaymentResponseDto(intent.getClientSecret());
    }


    public String checkPaymentIntentStatus(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return paymentIntent.getStatus();
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<?> handleStripeWebhook(@Valid @RequestBody ConfirmPaymentDto confirmPaymentDto) {
        confirmPaymentDto.getBillIds().forEach(billId -> billService.pay(billId));

        return new ResponseEntity<List<Long>>(confirmPaymentDto.getBillIds(), HttpStatus.ACCEPTED);

    }

    // Helper method to calculate order amount
    private Long calculateOrderAmount(List<Long> billIds) {
        return billService.computeAmountForBills(billIds)*100;
    }

}



