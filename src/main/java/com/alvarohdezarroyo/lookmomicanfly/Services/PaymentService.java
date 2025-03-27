package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Payment;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.PaymentRepository;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findPaymentById(String id){
        return paymentRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Payment ID does not exist.")
        );
    }

    private Payment findPaymentByPaymentIntentId(String id){
        return paymentRepository.findByPaymentIntentId(id).orElseThrow(
                ()-> new EntityNotFoundException("Payment Intent ID does not exist.")
        );
    }

    public Payment savePayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(PaymentIntent intent){
        final Payment payment= findPaymentByPaymentIntentId(intent.getId());
        payment.setStatus(intent.getStatus());
        return savePayment(payment);
    }

}
