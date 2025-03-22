package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.RandomGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransactionStatusService {

    @Autowired
    private final OrderService orderService;
    private final SaleService saleService;
    private final TransactionService transactionService;

    public TransactionStatusService(OrderService orderService, SaleService saleService, TransactionService transactionService) {
        this.orderService = orderService;
        this.saleService = saleService;
        this.transactionService = transactionService;
    }

    @Transactional
    public String changeSaleStatus(){
        AtomicInteger updatedSales=new AtomicInteger(0);
        saleService.getAllOngoingSales().forEach(
                sale -> {
                    saleService.changeSaleStatus(sale.getId(),switchSaleStatus(sale.getStatus(),sale));
                    updatedSales.incrementAndGet();
                }
        );
        return updatedSales.get()+" transactions were updated.";
    }

    @Transactional
    private SaleStatus switchSaleStatus(SaleStatus status, Sale sale){
        SaleStatus newStatus;
        switch (status){
            case PENDING ->{
                if(!generateRandomNumberComparison())
                    newStatus=SaleStatus.SHIPPED;
                else newStatus=SaleStatus.CANCELLED;
            }
            case SHIPPED -> newStatus=SaleStatus.RECEIVED;
            case RECEIVED -> newStatus=SaleStatus.VERIFYING;
            case VERIFYING -> {
                if(generateRandomNumberComparison())
                    newStatus=SaleStatus.FAILED;
                else newStatus=SaleStatus.AUTHENTICATED;
            }
            case AUTHENTICATED -> newStatus=SaleStatus.COMPLETED;
            default -> newStatus=null;
        }
        changeOrderStatus(newStatus,sale.getId());
        return newStatus;
    }

    @Transactional
    private void changeOrderStatus(SaleStatus saleStatus, String saleId){
        final OrderStatus orderStatus = switch (saleStatus){
            case SHIPPED -> OrderStatus.ON_THE_WAY_TO_US;
            case VERIFYING,RECEIVED -> OrderStatus.AUTHENTICATING;
            case FAILED -> OrderStatus.FAKE_PRODUCT;
            case AUTHENTICATED -> OrderStatus.AUTHENTICATED;
            case COMPLETED -> OrderStatus.SHIPPED;
            case CANCELLED -> OrderStatus.CANCELLED;
            case PENDING -> OrderStatus.WAITING;
        };
        orderService.changeOrderStatus(transactionService.getOrderIdFromTransaction(saleId), orderStatus);
    }

    private boolean generateRandomNumberComparison(){
        return RandomGenerator.generateRandomSingleDigitNumber()>8;
    }

}
