package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.EmailDetailsDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.PDFGeneratorErrorException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.ShippingLabelGeneratorErrorException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Sale;
import com.alvarohdezarroyo.lookmomicanfly.Models.TrackingNumber;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.PDFGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.ShippingLabelGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.FileValidator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private final EmailTemplateService emailTemplateService;
    private final JavaMailSender javaMailSender;

    public EmailSenderService(EmailTemplateService emailTemplateService, JavaMailSender javaMailSender) {
        this.emailTemplateService = emailTemplateService;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithNoAttachment(EmailDetailsDTO emailDetailsDTO) {
        try {
            final MimeMessage mimeMessage= generateEmail(emailDetailsDTO,null);
            sendEmail(mimeMessage);
        }
        catch (MessagingException | MailException e){
            log.error("There was an issue sending the email.");
        }
        catch (RuntimeException ex){
            log.error("Server error");
        }
    }

    public void sendEmailWithAttachment(EmailDetailsDTO emailDetailsDTO, Sale sale, TrackingNumber trackingNumber){
        try{
            final File file=new File(
                    generateSaleAndShippingEmail(sale,trackingNumber)
            );
            final MimeMessage mimeMessage= generateEmail(emailDetailsDTO,file);
            sendEmail(mimeMessage);
            FileValidator.removeShippingLabelDirectory();
        } catch (NullPointerException | MessagingException | PDFGeneratorErrorException |
                 ShippingLabelGeneratorErrorException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String generateSaleAndShippingEmail(Sale sale, TrackingNumber trackingNumber){
        final String shippingLabelPath= ShippingLabelGenerator.generateShippingLabel(sale.getId());
        return PDFGenerator.generateSalePDF(sale,trackingNumber, shippingLabelPath);
    }

    private void sendEmail(MimeMessage mimeMessage){
        try {
            javaMailSender.send(mimeMessage);
        }
        catch (MailException e){
            log.error(e.getMessage());
            log.error("There was an issue when sending the email.");
        }
        catch (RuntimeException ex){
            log.error(ex.getMessage());
            log.error("Server error.");
        }
    }

    private MimeMessage generateEmail(EmailDetailsDTO emailDetailsDTO, File file) throws MessagingException {
        try{
            final MimeMessage mimeMessage= javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            String content= emailTemplateService.addParamsToHTMLTemplate(emailDetailsDTO.getTemplate(),emailDetailsDTO.getContent());
            mimeMessageHelper.setTo(emailDetailsDTO.getRecipient());
            mimeMessageHelper.setFrom(emailDetailsDTO.getSender());
            mimeMessageHelper.setSubject(emailDetailsDTO.getSubject());
            mimeMessageHelper.setText(content,true);
            if(file!=null)
                mimeMessageHelper.addAttachment(file.getName(),file);
            return mimeMessage;
        }
        catch(Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

}
