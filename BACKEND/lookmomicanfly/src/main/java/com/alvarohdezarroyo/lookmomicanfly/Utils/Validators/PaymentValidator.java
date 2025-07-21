package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

    @Autowired
    private final PaymentService paymentService;

    public PaymentValidator(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void checkIfPaymentIntentIdIsValid(String paymentIntentId){
        if(!paymentService.findPaymentByPaymentIntentId(paymentIntentId)
                .getStatus().equalsIgnoreCase("requires_capture"))
            throw new FraudulentRequestException("Fraudulent request. Payment ID was already used.");
    }

}
