package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SaleDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.TransactionSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.SaleStatus;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.TrackingNumberAmountLimitReached;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.SaleRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private final SaleRepository saleRepository;
    private final TrackingNumberService trackingNumberService;

    public SaleService(SaleRepository saleRepository, TrackingNumberService trackingNumberService) {
        this.saleRepository = saleRepository;
        this.trackingNumberService = trackingNumberService;
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
            throw new TrackingNumberAmountLimitReached("You can only generate 3 tracking numbers for a sale.");
        trackingNumberService.saveSaleTrackingNumber(saleId);
    }

    public SaleDTO getSaleDTO(String id) throws Exception {
        return TransactionMapper.toSaleDTO(getSaleById(id),trackingNumberService.getSaleTrackingNumber(id).getCode());
    }

}
