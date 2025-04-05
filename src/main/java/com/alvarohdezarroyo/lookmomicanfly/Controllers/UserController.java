package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangePasswordRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangeUserFieldsRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.LoginRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.EmailParamsGenerator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.UserMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    private final AuthService authService;
    private final UserValidator userValidator;
    private final PostService postService;
    private final AddressService addressService;
    private final BankAccountService bankAccountService;
    private final PhoneNumberService phoneNumberService;
    private final EmailSenderService emailSenderService;

    UserController(UserService userService, AuthService authService, UserValidator userValidator, PostService postService, AddressService addressService, BankAccountService bankAccountService, PhoneNumberService phoneNumberService, EmailSenderService emailSenderService){
        this.userService=userService;
        this.authService = authService;
        this.userValidator = userValidator;
        this.postService = postService;
        this.addressService = addressService;
        this.bankAccountService = bankAccountService;
        this.phoneNumberService = phoneNumberService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping ("/register")
    public ResponseEntity<Map<String,String>> createUser(@RequestBody UserDTO user) throws Exception {
        UserValidator.emptyUserDTOFieldsValidator(user);
        user.setUserType(UserType.STANDARD.name());
        if(userValidator.checkUserByEmail(user.getEmail()))
            throw new EmailAlreadyInUseException("Email is already in use. Use a new one.");
        final User savedUser=userService.saveUser(
                UserMapper.toUser(user)
        );
        final String token= authService.authenticateUserAndGenerateToken(savedUser.getEmail(),user.getPassword());
        final UserDTO userDTO=UserMapper.toDTO(savedUser);
        emailSenderService.sendEmailWithNoAttachment(
                EmailParamsGenerator.generateRegistrationParams(userDTO)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success:",
                        token)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginAuthentication(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        userLogin(loginRequestDTO.getEmail(),loginRequestDTO.getPassword())
                ));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {




        return ResponseEntity.ok("success");
    }

    @PutMapping("/deactivate/")
    public ResponseEntity <Map<String,String>> deactivateAccount(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final User user=userValidator.returnUserById(userId);
        userService.deactivateAccount(userId);
        postService.deactivateAllUserPosts(userId);
        addressService.deactivateAllUserAddresses(userId);
        bankAccountService.deactivateAllUserBankAccounts(userId);
        phoneNumberService.deactivateAllUserPhoneNumbers(userId);
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateDeactivatedAccountParams(user.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success",userId));
    }

    @GetMapping("/addresses/")
    public ResponseEntity<Map<String,AddressDTO[]>> getUserAddresses(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Address> addresses=userService.getUserAddresses(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("addresses",
                        AddressMapper.addressListToAddressDTOArray(addresses)
                ));
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<Map<String,Object>> changeUserEmail(@RequestBody ChangeUserFieldsRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
        authService.checkFraudulentRequest(request.getUserId());
        userService.changeEmail(request.getUserId(), request.getNewField());
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateNewEmailParams(request.getNewField()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        "Email modification completed. User ID: '"+request.getUserId()+
                                "'. New email: '"+request.getNewField()+"'."));
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String,Object>> changeUserPassword(@RequestBody ChangePasswordRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        UserValidator.emptyChangePasswordFieldsValidator(request);
        authService.checkFraudulentRequest(request.getId());
        final User foundUser=userValidator.returnUserById(request.getId());
        userService.changeUserPassword(request);
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateNewPasswordParams(foundUser.getEmail()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        "Password modification completed. User ID: '"+request.getId()+"'."));
    }

    private String userLogin(String userEmail, String password){
        return authService.authenticateUserAndGenerateToken(userEmail,password);
    }

}
