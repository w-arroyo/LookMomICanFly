package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Models.Order;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingPostsService {

    @Autowired
    private final TransactionService transactionService;

    public MatchingPostsService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Transactional
    public Order saveTransactionAndGetOrder(Bid savedBid,Ask lowestAsk){
        return transactionService
                .createOrderSaleAndTransaction(lowestAsk,savedBid)
                .getOrder();
    }

    @Transactional
    public Sale saveTransactionAndGetSale(Ask savedAsk, Bid highestBid){
        return transactionService
                .createOrderSaleAndTransaction(savedAsk,highestBid)
                .getSale();
    }

}
