package com.alvarohdezarroyo.lookmomicanfly.Enums;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import lombok.Getter;

@Getter
public enum PhoneFormat {

    GERMANY("+49", 10),
    FRANCE("+33", 9),
    UNITED_KINGDOM("+44", 10),
    ITALY("+39", 10),
    SPAIN("+34", 9),
    NETHERLANDS("+31", 9),
    SWEDEN("+46", 9),
    SWITZERLAND("+41", 9),
    BELGIUM("+32", 9),
    AUSTRIA("+43", 9),
    NORWAY("+47", 8),
    DENMARK("+45", 8),
    FINLAND("+358", 9),
    POLAND("+48", 9),
    PORTUGAL("+351", 9),

    UNITED_STATES("+1", 10),
    MEXICO("+52", 10),
    BRAZIL("+55", 11),
    ARGENTINA("+54", 10),
    COLOMBIA("+57", 10),
    CHILE("+56", 9),
    PERU("+51", 9),
    ECUADOR("+593", 9),
    VENEZUELA("+58", 10),
    GUATEMALA("+502", 8),
    CUBA("+53", 8),

    CHINA("+86", 11),
    INDIA("+91", 10),
    JAPAN("+81", 10),
    SOUTH_KOREA("+82", 10),
    INDONESIA("+62", 10),
    PAKISTAN("+92", 10),
    PHILIPPINES("+63", 10),
    VIETNAM("+84", 9),
    THAILAND("+66", 9),
    MALAYSIA("+60", 9),

    AUSTRALIA("+61", 9),
    NEW_ZEALAND("+64", 9),
    FIJI("+679", 7),
    PAPUA_NEW_GUINEA("+675", 7),
    SAMOA("+685", 7),
    TONGA("+676", 5),
    VANUATU("+678", 7),
    SOLOMON_ISLANDS("+677", 7),
    NAURU("+674", 7),
    MICRONESIA("+691", 7);

    private final String phonePrefix;
    private final int numberLength;

    PhoneFormat(String phonePrefix, int numberLength) {
        this.phonePrefix = phonePrefix;
        this.numberLength = numberLength;
    }

    public static PhoneFormat checkIfPrefixExists(String prefix){
        for(PhoneFormat format: PhoneFormat.values()){
            if(format.getPhonePrefix().equals(prefix))
                return format;
        }
        throw new EntityNotFoundException("Phone format does not exist.");
    }

}
