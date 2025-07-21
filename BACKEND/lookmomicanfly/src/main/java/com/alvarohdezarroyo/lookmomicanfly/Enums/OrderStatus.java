package com.alvarohdezarroyo.lookmomicanfly.Enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    WAITING("Seller hasn't shipped the product yet."),
    CANCELLED("Seller did not ship the product in time."),
    ON_THE_WAY_TO_US("Product is on the way to us."),
    AUTHENTICATING("We are verifying if the product is authentic."),
    AUTHENTICATED("Good news! The product is original and will be shipped to you very soon."),
    FAKE_PRODUCT("The product failed the verification process."),
    SHIPPED("Order is on the way to you."),
    DELIVERED("Your order was completed. Thank you for shopping with us.");

    final String value;

    OrderStatus(String value){
        this.value=value;
    }

}
