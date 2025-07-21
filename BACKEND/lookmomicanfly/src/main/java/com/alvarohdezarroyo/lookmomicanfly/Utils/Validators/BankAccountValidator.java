package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidator {

    public static void checkBankAccountFormat(String number){
        if(number.length()!=24)
            throw new IllegalArgumentException("Bank account number must have 24 digits.");
        for(int character = 0; character < number.length(); character++) {
           switch(character){
               case 0,1->{
                   if(number.charAt(character)<'A'|| number.charAt(character)>'Z')
                       throw new IllegalArgumentException("First two characters of the bank account number must be capital letters.");
               }
               default -> {
                   if(number.charAt(character)<'0'|| number.charAt(character)>'9')
                       throw new IllegalArgumentException("Bank account must be 2 capital letters followed by 22 numbers");
               }
           }
        }
    }

}
