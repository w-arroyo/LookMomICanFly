package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.TransactionRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.EmailParamsGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;
    private final SaleService saleService;
    private final TrackingNumberService trackingNumberService;
    private final EmailSenderService emailSenderService;
    private final SMSService smsService;

    public TransactionService(TransactionRepository transactionRepository, OrderService orderService, SaleService saleService, TrackingNumberService trackingNumberService, EmailSenderService emailSenderService, SMSService smsService) {
        this.transactionRepository = transactionRepository;
        this.orderService = orderService;
        this.saleService=saleService;
        this.trackingNumberService = trackingNumberService;
        this.emailSenderService = emailSenderService;
        this.smsService = smsService;
    }

    @Transactional
    private Transaction saveTransaction(Order order, Sale sale){
        final TrackingNumber trackingNumber=trackingNumberService.saveSaleTrackingNumber(sale.getId());
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateOrderParams(order));
        emailSenderService.sendEmailWithAttachment(EmailParamsGenerator.generateSaleParams(sale,trackingNumber),sale,trackingNumber);
        final Transaction transaction=TransactionMapper.createTransaction(order,sale);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createOrderSaleAndTransaction(Ask ask, Bid bid){
        return saveTransaction(createOrder(bid),createSale(ask));
    }

    @Transactional
    private Order createOrder(Bid bid){
        final Order order=TransactionMapper.createOrder(bid);
        smsService.sendSMS(order.getBid().getUser().getId(), "Your LOOK MOM I CAN FLY order is on! Your bid for '" + order.getBid().getProduct().getName() + "' in size '" + order.getBid().getSize().getValue() + "' was accepted. Check your email and profile for further details.");
        return orderService.saveOrder(order);
    }

    @Transactional
    private Sale createSale(Ask ask){
        final Sale sale=TransactionMapper.createSale(ask);
        smsService.sendSMS(sale.getAsk().getUser().getId(), "Congrats! Your item '" + sale.getAsk().getProduct().getName() + "' in size '" + sale.getAsk().getSize().getValue() + "' was sold on LOOK MOM I CAN FLY. Please check your email and profile to follow the shipping instructions.");
        return saleService.saveSale(sale);
    }

    public String getOrderIdFromTransaction(String saleId){
        return transactionRepository.getOrderIdFromTransaction(saleId).orElseThrow(
                ()-> new EntityNotFoundException("Sale ID does not exist.")
        );
    }

    public LocalDateTime getTransactionDate(String id){
        return transactionRepository.getTransactionDate(id);
    }

}
