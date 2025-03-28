package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.BankAccountDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.BankAccount;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.BankAccountService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.BankAccountMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.BankAccountValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bank_accounts")
public class BankAccountController {

    @Autowired
    private final BankAccountService bankAccountService;
    private final AuthService authService;
    private final UserValidator userValidator;

    public BankAccountController(BankAccountService bankAccountService, AuthService authService, UserValidator userValidator) {
        this.bankAccountService = bankAccountService;
        this.authService = authService;
        this.userValidator = userValidator;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBankAccount(@RequestBody BankAccountDTO bankAccountDTO){
        GlobalValidator.checkIfTwoFieldsAreEmpty(bankAccountDTO.getNumber(), bankAccountDTO.getUserId());
        authService.checkFraudulentRequest(bankAccountDTO.getUserId());
        BankAccountValidator.checkBankAccountFormat(bankAccountDTO.getNumber());
        final User user= userValidator.returnUserById(bankAccountDTO.getUserId());
        bankAccountService.deactivateAllUserBankAccounts(user.getId());
        bankAccountService.saveBankAccount(
                BankAccountMapper.toBankAccount(bankAccountDTO,user)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/user/")
    public ResponseEntity<Map<String,BankAccountDTO>> getUserBankAccount(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final BankAccount bankAccount=bankAccountService.findUserActiveAccount(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("bankAccount",
                        BankAccountMapper.toDTO(bankAccount)));
    }

}
