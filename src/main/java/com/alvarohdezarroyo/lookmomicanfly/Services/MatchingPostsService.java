package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.PostValidator;
import org.springframework.stereotype.Service;

@Service
public class MatchingPostsService {

    public static boolean checkForMatchingAsk(Bid savedBid,Ask lowestAsk){
        if(lowestAsk==null)
            return false;
        if(lowestAsk.getAmount()< savedBid.getAmount())
            throw new IllegalArgumentException("Bid amount can not surpass lowest ask amount.");
        else if(lowestAsk.getAmount()> savedBid.getAmount())
            return false;
        else{
            PostValidator.checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(lowestAsk.getUser().getId(),savedBid.getUser().getId());
            return true;
        }
    }

    public static boolean checkForMatchingBid(Ask savedAsk, Bid highestBid){
        if(highestBid==null)
            return false;
        if(highestBid.getAmount()>savedAsk.getAmount())
            throw new IllegalArgumentException("Ask amount can not be lower than highest bid amount.");
        else if(savedAsk.getAmount()> highestBid.getAmount())
            return false;
        else{
            PostValidator.checkIfUserCreatingThePostIsTheSameAsTheBestOfferOne(highestBid.getUser().getId(),savedAsk.getUser().getId());
            return true;
        }
    }



}
