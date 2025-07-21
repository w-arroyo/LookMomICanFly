package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.PaymentDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PaymentService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.PaymentMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${app.stripeSecretKey}")
    private String stripeSecretKey;

    @Autowired
    private final PaymentService paymentService;
    private final AuthService authService;

    public PaymentController(PaymentService paymentService, AuthService authService) {
        this.paymentService = paymentService;
        this.authService = authService;
    }

    @PostMapping("/create/")
    public ResponseEntity<PaymentDTO> createPayment(@RequestParam String userId) throws StripeException {
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final PaymentIntent intent= PaymentMapper.toPaymentIntent(userId);
        Payment payment=PaymentMapper.toPayment(intent);
        payment=paymentService.savePayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaymentMapper.toDTO(payment,intent));
    }

}
