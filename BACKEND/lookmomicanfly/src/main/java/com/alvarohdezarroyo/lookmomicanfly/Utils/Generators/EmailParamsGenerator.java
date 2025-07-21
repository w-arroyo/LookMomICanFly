package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.DTO.EmailDetailsDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Calculators.AmountCalculator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailParamsGenerator {

    public static EmailDetailsDTO generateRegistrationParams(UserDTO userDTO){
        return new EmailDetailsDTO(userDTO.getEmail(),"Welcome to LOOK MOM I CAN FLY!","Registration",AppConfig.getEmail(),
                Map.of("user",userDTO));
    }

    public static EmailDetailsDTO generateSavedBidParams(Bid bid){
        return new EmailDetailsDTO(bid.getUser().getEmail(), "Your bid was placed", "Bid", AppConfig.getEmail(),
                Map.of("bid",bid,
                        "amount", AmountCalculator.getBidTotal(bid)));
    }

    public static EmailDetailsDTO generateSavedAskParams(Ask ask){
        return new EmailDetailsDTO(ask.getUser().getEmail(), "Your ask was placed", "Ask", AppConfig.getEmail(),
                Map.of("ask",ask,
                        "amount", AmountCalculator.getAskPayout(ask)));
    }

    public static EmailDetailsDTO generateOrderParams(Order order){
        return new EmailDetailsDTO(order.getBid().getUser().getEmail(), "Order confirmed", "Order", AppConfig.getEmail(),
                Map.of("order",order,
                        "amount", AmountCalculator.getBidTotal(order.getBid())));
    }

    public static EmailDetailsDTO generateSaleParams(Sale sale, TrackingNumber trackingNumber){
        return new EmailDetailsDTO(sale.getAsk().getUser().getEmail(), "Sale confirmed", "Sale", AppConfig.getEmail(),
                Map.of("sale",sale,
                        "amount", AmountCalculator.getAskPayout(sale.getAsk()),
                        "tracking",trackingNumber));
    }

    public static EmailDetailsDTO generateDeactivatedAccountParams(String email){
        return new EmailDetailsDTO(email,"We're sad to see you go","Deactivate",AppConfig.getEmail(),
                null);
    }

    public static EmailDetailsDTO generateNewEmailParams(String email){
        return new EmailDetailsDTO(email,"This is you new email","NewEmail",AppConfig.getEmail(),
                null);
    }

    public static EmailDetailsDTO generateNewPasswordParams(String email){
        return new EmailDetailsDTO(email,"Your password was changed","NewPassword",AppConfig.getEmail(),
                null);
    }

    public static EmailDetailsDTO generateFailedPaymentParams(Bid bid){
        return new EmailDetailsDTO(bid.getUser().getEmail(),"Your payment was declined","FailedPayment",AppConfig.getEmail(),
                Map.of("bid",bid));
    }

    public static EmailDetailsDTO generateProductAuthenticatedParams(Sale sale){
        return new EmailDetailsDTO(sale.getAsk().getUser().getEmail(),"Product successfully authenticated","AuthenticationPassed",AppConfig.getEmail(),
                Map.of("sale",sale,
                "amount",AmountCalculator.getAskPayout(sale.getAsk()
                        )));
    }

    public static EmailDetailsDTO generateFakeProductParams(Sale sale){
        return new EmailDetailsDTO(sale.getAsk().getUser().getEmail(),"Your product was rejected","AuthenticationFailed",AppConfig.getEmail(),
                Map.of("sale",sale
                        ));
    }

    public static EmailDetailsDTO generateAuthenticatedOrderParams(Order order){
        return new EmailDetailsDTO(order.getBid().getUser().getEmail(),"Your order passed the authentication process","OrderAuthenticated",AppConfig.getEmail(),
                Map.of("order",order));
    }

    public static EmailDetailsDTO generateFailedOrderParams(Order order){
        return new EmailDetailsDTO(order.getBid().getUser().getEmail(),"Your order failed the authentication process","FailedOrder",AppConfig.getEmail(),
                Map.of("order",order));
    }

    public static EmailDetailsDTO generateCancelledOrderParams(Order order){
        return new EmailDetailsDTO(order.getBid().getUser().getEmail(),"Your order was cancelled","CancelledOrder",AppConfig.getEmail(),
                Map.of("order",order));
    }

    public static EmailDetailsDTO generateCancelledSaleParams(Sale sale){
        return new EmailDetailsDTO(sale.getAsk().getUser().getEmail(),"Your sale was cancelled","CancelledSale",AppConfig.getEmail(),
                Map.of("sale",sale
                ));
    }

    public static EmailDetailsDTO generateOrderCompletedParams(Order order, TrackingNumber trackingNumber){
        return new EmailDetailsDTO(order.getBid().getUser().getEmail(),"Your order was completed","CompletedOrder",AppConfig.getEmail(),
                Map.of("order",order,
                        "tracking",trackingNumber));
    }

    public static EmailDetailsDTO generateNewTrackingSaleParams(Sale sale, TrackingNumber trackingNumber){
        return new EmailDetailsDTO(sale.getAsk().getUser().getEmail(),"Here's your new shipping label","NewLabel",AppConfig.getEmail(),
                Map.of("sale",sale,
                        "tracking",trackingNumber
                ));
    }

    public static EmailDetailsDTO newsletterParams(String email) {
        return new EmailDetailsDTO(email, "You are now subscribed", "Newsletter", AppConfig.getEmail(),
                null);
    }

}
