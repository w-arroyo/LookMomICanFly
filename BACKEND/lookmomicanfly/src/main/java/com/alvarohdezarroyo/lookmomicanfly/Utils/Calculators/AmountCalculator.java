package com.alvarohdezarroyo.lookmomicanfly.Utils.Calculators;

import com.alvarohdezarroyo.lookmomicanfly.Models.Ask;
import com.alvarohdezarroyo.lookmomicanfly.Models.Bid;
import org.springframework.stereotype.Component;

@Component
public class AmountCalculator {

    public static int getAskPayout(Ask ask){
        int amount=ask.getAmount();
        final double percentage= (double) ask.getSellingFee().getPercentage() /100;
        amount-= (int) (amount*percentage);
        amount-=ask.getShippingFee();
        return amount;
    }

    public static double getBidTotal(Bid bid){
        return (bid.getAmount()+bid.getOperationalFee()+bid.getShippingOption().getPrice());
    }

}
