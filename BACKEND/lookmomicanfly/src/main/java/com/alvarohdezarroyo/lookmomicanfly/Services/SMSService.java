package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Models.PhoneNumber;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SMSService {

    @Autowired
    private final PhoneNumberService phoneNumberService;

    @Value("${app.twilio.accountSid}")
    String accountSid;

    @Value("${app.twilio.authToken}")
    String authToken;

    @Value("${app.twilio.number}")
    String appPhoneNumber;

    public SMSService(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @PostConstruct
    // I save it with this annotation cause if I don't the values are not loaded and Twilio.init throws an exception
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSMS(String userId, String messageBody) {
        final PhoneNumber phoneNumberTo = getUserPhoneNumber(userId);
        if (phoneNumberTo != null) {
            try {
                final Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(phoneNumberTo.getPrefix() + phoneNumberTo.getNumber()),
                                new com.twilio.type.PhoneNumber(appPhoneNumber),
                                messageBody
                        )
                        .create();
            } catch (ApiException e) {
                log.error(e.getMessage());
            } catch (Exception ex) {
                log.error("Something went wrong when sending SMS.");
            }
        }
    }

    private PhoneNumber getUserPhoneNumber(String userId) {
        return phoneNumberService.getUserPhoneNumber(userId);
    }

}
