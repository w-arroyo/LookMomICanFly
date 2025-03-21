package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.TrackingNumberRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.TrackingNumberGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackingNumberService {

    @Autowired
    private final TrackingNumberRepository trackingNumberRepository;

    public TrackingNumberService(TrackingNumberRepository trackingNumberRepository) {
        this.trackingNumberRepository = trackingNumberRepository;
    }

    public void useTrackingNumber(String id){
        if(trackingNumberRepository.useTrackingNumber(id)<1)
            throw new EntityNotFoundException("Tracking number id does not exist.");
    }

    @Transactional
    private TrackingNumber saveTrackingNumber(TrackingNumber trackingNumber){
        return trackingNumberRepository.save(trackingNumber);
    }

    private TrackingNumber createTrackingNumber(boolean status){
        return new TrackingNumber(TrackingNumberGenerator.generateTrackingNumber(),status);
    }

    @Transactional
    public void saveOrderTrackingNumber(String orderId){
        trackingNumberRepository.insertIntoOrdersTrackingTable(saveTrackingNumber(createTrackingNumber(true)).getId(),orderId);
    }

    @Transactional
    public void saveSaleTrackingNumber(String saleId){
        trackingNumberRepository.insertIntoSalesTrackingTable(saveTrackingNumber(createTrackingNumber(false)).getId(),saleId);
    }

}
