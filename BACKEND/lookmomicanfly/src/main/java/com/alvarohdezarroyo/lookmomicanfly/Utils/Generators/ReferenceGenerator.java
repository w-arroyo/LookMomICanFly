package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import org.springframework.stereotype.Component;

@Component
public class ReferenceGenerator {

    public static final int MAX_VALID_LENGTH = 36;
    public static final int MIN_VALUE_FIRST_SECTION = 4;
    public static final int MIN_VALUE_SECOND_SECTION = 9;
    public static final int MIN_VALUE_THIRD_SECTION = 19;
    public static final int MIN_VALUE_FOURTH_SECTION = 23;
    public static final int MIN_VALUE_FIFTH_SECTION = 30;
    public static final int MIN_VALUE_SIXTH_SECTION = 33;

    public static String generateRandomReference(){
        final StringBuilder reference=new StringBuilder();
        int character=0;
        while(character< MAX_VALID_LENGTH){
            if((character> MIN_VALUE_FIRST_SECTION && character<MIN_VALUE_SECOND_SECTION) ||(character>MIN_VALUE_THIRD_SECTION && character<MIN_VALUE_FOURTH_SECTION) || (character>MIN_VALUE_FIFTH_SECTION && character<MIN_VALUE_SIXTH_SECTION))
                reference.append(RandomGenerator.generateRandomSingleDigitNumber());
            else addRandomLetter(reference);
            character++;
        }
        return reference.toString();
    }

    private static void addRandomLetter(StringBuilder string){
        if(RandomGenerator.generateRandomSingleDigitNumber()%2==0)
            string.append(RandomGenerator.generateRandomUpperCaseLetter());
        else string.append(RandomGenerator.generateRandomLowerCaseLetter());
    }

}
