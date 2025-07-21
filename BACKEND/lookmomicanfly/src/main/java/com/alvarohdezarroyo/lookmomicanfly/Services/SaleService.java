package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.TrackingNumberAmountLimitReachedException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SaleRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.EmailParamsGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private final SaleRepository saleRepository;
    private final TrackingNumberService trackingNumberService;
    private final EmailSenderService emailSenderService;

    public SaleService(SaleRepository saleRepository, TrackingNumberService trackingNumberService, EmailSenderService emailSenderService) {
        this.saleRepository = saleRepository;
        this.trackingNumberService = trackingNumberService;
        this.emailSenderService = emailSenderService;
    }

    public Sale getSaleById(String saleId){
        return saleRepository.findById(saleId).orElseThrow(
                ()->new EntityNotFoundException("Sale ID does not exist.")
        );
    }

    @Transactional
    public Sale saveSale(Sale sale){
        return saleRepository.save(sale);
    }

    public int getUserNumberSalesDuringLastThreeMonths(String userId){
        return saleRepository.getUserSalesDuringPastThreeMonths(userId);
    }

    public List<Sale> getAllOngoingSales(){
        return saleRepository.getAllOngoingSales();
    }

    @Transactional
    public void changeSaleStatus(String saleId, SaleStatus status){
        if(saleRepository.changeSaleStatus(saleId,status.name())<1)
            throw new RuntimeException("Server error. Unable to update sale status.");
        if(status.equals(SaleStatus.SHIPPED))
            trackingNumberService.useTrackingNumber(trackingNumberService.getSaleTrackingNumber(saleId).getId());
    }

    @Transactional
    public void generateNewSaleTrackingNumber(String saleId){
        if(trackingNumberService.getSaleAmountOfTrackingNumbers(getSaleById(saleId).getId())>2)
            throw new TrackingNumberAmountLimitReachedException("You can only generate 3 tracking numbers for a sale.");
        final Sale foundSale=getSaleById(saleId);
        final TrackingNumber trackingNumber = trackingNumberService.saveSaleTrackingNumber(saleId);
        emailSenderService.sendEmailWithAttachment(EmailParamsGenerator.generateNewTrackingSaleParams(foundSale,trackingNumber),foundSale,trackingNumber);
    }

    public String getSaleCurrentTrackingNumberCode(String saleId){
        return trackingNumberService.getSaleTrackingNumber(saleId).getCode();
    }

    public List<Sale> getAllUserSales(String userId){
        return saleRepository.getAllUserSales(userId);
    }

    public Integer getProductBySizeLastSaleAmount(String productId, String size){
        return saleRepository.getLastSaleAmount(productId,size)
                .orElse(null);
    }

}
