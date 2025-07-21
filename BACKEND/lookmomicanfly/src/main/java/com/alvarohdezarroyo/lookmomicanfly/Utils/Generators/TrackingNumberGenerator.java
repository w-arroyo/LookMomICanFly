package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import org.springframework.stereotype.Component;

@Component
public class TrackingNumberGenerator {


    private static void addCertainAmountOfLettersToStringBuilder(StringBuilder string, int range){
        for (int position=0; position<range; position++) {
            string.append(RandomGenerator.generateRandomUpperCaseLetter());
        }
    }

    private static void addCertainAmountOfDigitsToStringBuilder(StringBuilder stringBuilder, int range){
        for (int position=0;  position<range; position++) {
            stringBuilder.append(RandomGenerator.generateRandomSingleDigitNumber());
        }
    }

    public static String generateTrackingNumber(){
        final StringBuilder trackingNumber=new StringBuilder();
        trackingNumber.append(RandomGenerator.generateRandomSingleDigitNumber());
        trackingNumber.append(RandomGenerator.generateRandomUpperCaseLetter());
        addCertainAmountOfDigitsToStringBuilder(trackingNumber,3);
        addCertainAmountOfLettersToStringBuilder(trackingNumber,3);
        addCertainAmountOfDigitsToStringBuilder(trackingNumber,8);
        return trackingNumber.toString();
    }

}
