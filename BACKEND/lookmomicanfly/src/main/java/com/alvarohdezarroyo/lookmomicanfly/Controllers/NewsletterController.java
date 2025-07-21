package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.SuccessfulRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.EmailSenderService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.EmailParamsGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    @Autowired
    private final EmailSenderService emailSenderService;

    public NewsletterController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/")
    public ResponseEntity<SuccessfulRequestDTO> subscribeToNewsletter(@RequestParam String email) {
        GlobalValidator.checkIfAFieldIsEmpty(email);
        emailSenderService.sendEmailWithNoAttachment(
                EmailParamsGenerator.newsletterParams(email)
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO("Successfully subscribed to the newsletter.")
        );
    }
}
