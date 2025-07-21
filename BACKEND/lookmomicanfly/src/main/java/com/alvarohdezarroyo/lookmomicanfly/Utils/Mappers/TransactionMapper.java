package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
import com.alvarohdezarroyo.lookmomicanfly.Enums.OrderStatus;
import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Services.TransactionService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Calculators.AmountCalculator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.DateGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.ReferenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {

    @Autowired
    private final ProductMapper productMapper;
    private final TransactionService transactionService;

    public TransactionMapper(ProductMapper productMapper, TransactionService transactionService) {
        this.productMapper = productMapper;
        this.transactionService = transactionService;
    }

    public TransactionSummaryDTO saleToTransactionSummaryDTO(Sale sale) throws Exception {
        final TransactionSummaryDTO transaction=new TransactionSummaryDTO();
        transaction.setProduct(productMapper.toSummary(sale.getAsk().getProduct()));
        transaction.setReference(sale.getReference());
        transaction.setSize(sale.getAsk().getSize().getValue());
        transaction.setAddress(AddressMapper.toDTO(sale.getAsk().getAddress()));
        transaction.setAmount(AmountCalculator.getAskPayout(sale.getAsk()));
        transaction.setId(sale.getId());
        return transaction;
    }

    public TransactionSummaryDTO orderToTransactionSummaryDTO(Order order) throws Exception {
        final TransactionSummaryDTO transaction=new TransactionSummaryDTO();
        transaction.setReference(order.getReference());
        transaction.setSize(order.getBid().getSize().getValue());
        transaction.setProduct(productMapper.toSummary(order.getBid().getProduct()));
        transaction.setAddress(AddressMapper.toDTO(order.getBid().getAddress()));
        transaction.setAmount(AmountCalculator.getBidTotal(order.getBid()));
        transaction.setId(order.getId());
        return transaction;
    }

    private void fillTransactionDTOFields(TransactionDTO transaction, String reference, String tracking, Address address, int amount, String size, Product product, TransactionStatusDTO status, String id) throws Exception {
        transaction.setReference(reference);
        transaction.setAddress(AddressMapper.toDTO(address));
        transaction.setAmount(amount);
        transaction.setSize(size);
        if(tracking!=null)
            transaction.setTrackingNumber(tracking);
        else transaction.setTrackingNumber("Not available yet.");
        transaction.setProduct(productMapper.toSummary(product));
        transaction.setStatus(status);
        transaction.setId(id);
    }

    public SaleDTO toSaleDTO(Sale sale, String tracking) throws Exception {
        final SaleDTO saleDTO=new SaleDTO();
        fillTransactionDTOFields(saleDTO, sale.getReference(),tracking,sale.getAsk().getAddress(),sale.getAsk().getAmount(),sale.getAsk().getSize().getValue(),sale.getAsk().getProduct(), new TransactionStatusDTO(sale.getStatus().name().replace("_"," "),sale.getStatus().getValue()), sale.getId());
        saleDTO.setShippingFee(sale.getAsk().getShippingFee());
        saleDTO.setPercentage(sale.getAsk().getSellingFee().getPercentage());
        return saleDTO;
    }

    public OrderDTO toOrderDTO(Order order, String tracking) throws Exception {
        final OrderDTO orderDTO=new OrderDTO();
        fillTransactionDTOFields(orderDTO,order.getReference(),tracking,order.getBid().getAddress(),order.getBid().getAmount(),order.getBid().getSize().getValue(),order.getBid().getProduct(),new TransactionStatusDTO(order.getStatus().name().replace("_"," "),order.getStatus().getValue()),order.getId());
        orderDTO.setOperationalFee(order.getBid().getOperationalFee());
        orderDTO.setShippingOption(ShippingOptionMapper.toDTO(order.getBid().getShippingOption()));
        return orderDTO;
    }

    public static Transaction createTransaction(Order order, Sale sale){
        final Transaction transaction=new Transaction();
        transaction.setSale(sale);
        transaction.setOrder(order);
        return transaction;
    }

    public static Order createOrder(Bid bid){
        final Order order=new Order();
        order.setReference(ReferenceGenerator.generateRandomReference());
        order.setBid(bid);
        order.setStatus(OrderStatus.WAITING);
        return order;
    }

    public static Sale createSale(Ask ask){
        final Sale sale=new Sale();
        sale.setReference(ReferenceGenerator.generateRandomReference());
        sale.setAsk(ask);
        sale.setStatus(SaleStatus.PENDING);
        return sale;
    }

    private TransactionOverviewDTO toTransactionOverviewDTO(String id,Product product,String status,String size,int amount,String date){
        return new TransactionOverviewDTO(
            id,size,status,amount,productMapper.toSummary(product),date
        );
    }

    public List<TransactionOverviewDTO> salesToOverview(List<Sale> sales){
        return sales.stream().map(sale -> toTransactionOverviewDTO(
                sale.getId(),
                sale.getAsk().getProduct(),
                sale.getStatus().name().replace("_"," "),
                sale.getAsk().getSize().getValue(),
                AmountCalculator.getAskPayout(sale.getAsk()),
                DateGenerator.formatDate(
                        transactionService.getTransactionDate(sale.getId())
                )
        )).toList();
    }

    public List<TransactionOverviewDTO> ordersToOverview(List<Order> orders){
        return orders.stream().map(order -> toTransactionOverviewDTO(
                order.getId(),
                order.getBid().getProduct(),
                order.getStatus().name().replace("_"," "),
                order.getBid().getSize().getValue(),
                (int)(AmountCalculator.getBidTotal(order.getBid())),
                DateGenerator.formatDate(
                        transactionService.getTransactionDate(order.getId())
                )
        )).toList();
    }

}
