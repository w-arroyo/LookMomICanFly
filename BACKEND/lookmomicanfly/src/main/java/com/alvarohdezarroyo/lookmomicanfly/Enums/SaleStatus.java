package com.alvarohdezarroyo.lookmomicanfly.Enums;

import lombok.Getter;

@Getter
public enum SaleStatus {

    PENDING("You haven't shipped the product yet."),
    CANCELLED("Your sale was cancelled. You did not ship the product in time."),
    SHIPPED("Your product is on route to us."),
    RECEIVED("We have your product."),
    VERIFYING("We are authenticating your product."),
    FAILED("Your product is not authentic and failed the process."),
    AUTHENTICATED("Your product passed authentication process. We'll process your payout very soon."),
    COMPLETED("Your sale was completed and we processed your payout.");

    final String value;

    SaleStatus(String value){
        this.value=value;
    }

}
