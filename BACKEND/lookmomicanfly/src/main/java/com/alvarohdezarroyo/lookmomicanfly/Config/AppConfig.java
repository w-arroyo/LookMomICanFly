package com.alvarohdezarroyo.lookmomicanfly.Config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {

    private final static String SHIPPING_LABELS_PATH = System.getProperty("user.dir")+ File.separator+"Shipping Labels"+File.separator;
    private static String aesKey, emailAddress, tokenSigningKey, tokenPrefix, userTokensPrefix;
    private static int tokenLength;
    private static double sellingShippingPrice, operationalFee;

    @Value("${app.aesKey}")
    public void setAesKey(String value) {
        aesKey= value;
    }

    @Value("${app.emailAddress}")
    public void setEmailAddress(String value){
        emailAddress=value;
    }

    @Value("${app.tokenLength}")
    public void setTokenLength(String value){
        tokenLength=Integer.parseInt(value);
    }

    @Value("${app.redisTokenPrefix}")
    public void setJwtTokenPrefix(String value) {
        tokenPrefix = value;
    }

    @Value("${app.redisUserIdSetPrefix}")
    public void setUserTokensPrefixValue(String value) {
        userTokensPrefix = value;
    }

    @Value("${app.tokenKey}")
    public void setTokenKey(String value){
        tokenSigningKey=value;
    }

    @Value("${app.sellingShippingFee}")
    public void setSellingCost(String value){
        sellingShippingPrice=Double.parseDouble(value);
    }

    @Value("${app.operationalFee}")
    public void setOperational(String value){
        operationalFee=Double.parseDouble(value);
    }

    public static String getKey(){
        return aesKey;
    }

    public static String getShippingLabelsPath(){
        return SHIPPING_LABELS_PATH;
    }

    public static String getEmail(){
        return emailAddress;
    }

    public static int getTokenDuration(){
        return tokenLength;
    }

    public static double getSellingShipping(){
        return sellingShippingPrice;
    }

    public static double getOperationalBuyingFee(){
        return operationalFee;
    }

    public static String getJwtTokenPrefix() {
        return tokenPrefix;
    }

    public static String getUserTokensPrefixValue() {
        return userTokensPrefix;
    }

    public static SecretKey getTokenSigningSecretKey(){
        return Keys.hmacShaKeyFor(tokenSigningKey.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
