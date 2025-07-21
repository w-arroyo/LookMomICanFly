package com.alvarohdezarroyo.lookmomicanfly.ExceptionHandlers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.FailedRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.*;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.UnexpectedException;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<FailedRequestDTO> emailAlreadyInUseHandler(EmailAlreadyInUseException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(EmptyFieldsException.class)
    // NEEDS IMPROVEMENT
    public ResponseEntity<FailedRequestDTO> emptyFieldsHandler(EmptyFieldsException ex){
        if(ex.getMessage()==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getEmptyFields()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<FailedRequestDTO> entityNotFoundHandler(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(LoginUnsuccessfulException.class)
    public ResponseEntity<FailedRequestDTO> LoginUnsuccessfulHandler(LoginUnsuccessfulException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(FraudulentRequestException.class)
    public ResponseEntity<FailedRequestDTO> fraudulentRequestHandler(FraudulentRequestException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<FailedRequestDTO> unauthorizedRequestExceptionHandler(UnauthorizedRequestException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<FailedRequestDTO> badCredentialsHandler(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<FailedRequestDTO> noDataFoundHandler(NoDataFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(SameValuesException.class)
    public ResponseEntity<FailedRequestDTO> sameValuesHandler(SameValuesException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailedRequestDTO> runTimeExceptionHandler(RuntimeException ex){

        Arrays.stream(ex.getStackTrace()).toList().forEach(e ->
                log.error(e.toString())
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailedRequestDTO> exceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<FailedRequestDTO> unexpectedExceptionHandler(UnexpectedException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<FailedRequestDTO> illegalArgumentExceptionHandler(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<FailedRequestDTO> stripeSignatureVerificationExceptionHandler(SignatureVerificationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<FailedRequestDTO> stripeExceptionHandler(StripeException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(PaymentChargeUnsuccessfulException.class)
    public ResponseEntity<FailedRequestDTO> paymentChargeUnsuccessfulExceptionHandler(PaymentChargeUnsuccessfulException ex){
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(TrackingNumberAmountLimitReachedException.class)
    public ResponseEntity<FailedRequestDTO> trackingNumberAmountLimitReachedExceptionHandler(TrackingNumberAmountLimitReachedException ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(RejectedPostException.class)
    public ResponseEntity<FailedRequestDTO> rejectedPostExceptionHandler(RejectedPostException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(ProductAlreadyLikedException.class)
    public ResponseEntity<FailedRequestDTO> productAlreadyLikedExceptionHandler(ProductAlreadyLikedException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<FailedRequestDTO> messagingExceptionHandler(MessagingException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<FailedRequestDTO> invalidTokenExceptionHandler(InvalidTokenException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(PDFGeneratorErrorException.class)
    public ResponseEntity<FailedRequestDTO> pdfGeneratorErrorExceptionHandler(PDFGeneratorErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

    @ExceptionHandler(ShippingLabelGeneratorErrorException.class)
    public ResponseEntity<FailedRequestDTO> shippingLabelGeneratorErrorExceptionHandler(ShippingLabelGeneratorErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailedRequestDTO(ex.getMessage()));
    }

}
