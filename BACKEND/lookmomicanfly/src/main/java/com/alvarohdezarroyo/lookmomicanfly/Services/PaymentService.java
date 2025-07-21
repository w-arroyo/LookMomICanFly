package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findPaymentByPaymentIntentId(String id){
        return paymentRepository.findByPaymentIntentId(id).orElseThrow(
                ()-> new EntityNotFoundException("Payment Intent ID does not exist.")
        );
    }

    @Transactional
    public Payment savePayment(Payment payment){
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment updatePayment(PaymentIntent intent){
        final Payment payment= findPaymentByPaymentIntentId(intent.getId());
        payment.setStatus(intent.getStatus());
        return savePayment(payment);
    }

    @Transactional
    public boolean takePayment(String paymentIntentId) throws StripeException {
        final Payment payment= findPaymentByPaymentIntentId(paymentIntentId);
        final PaymentIntent paymentIntent=PaymentIntent.retrieve(payment.getPaymentIntentId())
                .capture(); // takes the money
        updatePayment(paymentIntent);
        return paymentIntent.getStatus().equals("succeeded");
    }

}
