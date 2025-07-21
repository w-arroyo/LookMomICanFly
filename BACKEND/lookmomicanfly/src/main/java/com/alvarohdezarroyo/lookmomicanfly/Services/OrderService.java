package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.OrderRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.EmailParamsGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    private final TrackingNumberService trackingNumberService;
    private final EmailSenderService emailSenderService;

    public OrderService(OrderRepository orderRepository, TrackingNumberService trackingNumberService, EmailSenderService emailSenderService) {
        this.orderRepository = orderRepository;
        this.trackingNumberService = trackingNumberService;
        this.emailSenderService = emailSenderService;
    }

    public Order getOrderById(String orderId){
        return orderRepository.findById(orderId).orElseThrow(
                ()->new EntityNotFoundException("Order ID does not exist.")
        );
    }

    @Transactional
    public String completeShippedOrders(){
        return orderRepository.completeShippedOrders()+" orders were completed.";
    }

    @Transactional
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    @Transactional
    public void changeOrderStatus(String orderId, OrderStatus status) {
        if(orderRepository.changeOrderStatus(orderId,status.name())<1)
            throw new RuntimeException("Server error. Unable to update order status.");
        checkIfOrderNeedsEmail(orderId);
        if(status.equals(OrderStatus.SHIPPED))
            saveOrderTrackingNumber(orderId);
    }

    @Transactional
    private TrackingNumber saveOrderTrackingNumber(String orderId){
        return trackingNumberService.saveOrderTrackingNumber(orderId);
    }


    public String getOrderTrackingNumber(String orderId){
        return trackingNumberService.getOrderTrackingNumber(orderId);
    }

    public List<Order> getAllUserOrders(String userId){
        return orderRepository.getAUserOrders(userId);
    }

    private void checkIfOrderNeedsEmail(String orderId){
        final Order order=getOrderById(orderId);
        switch (order.getStatus()){
            case CANCELLED -> emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateCancelledOrderParams(order));
            case AUTHENTICATED -> emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateAuthenticatedOrderParams(order));
            case FAKE_PRODUCT -> emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateFailedOrderParams(order));
            case SHIPPED -> emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateOrderCompletedParams(order,
                    saveOrderTrackingNumber(order.getId())));
        }
    }

}
