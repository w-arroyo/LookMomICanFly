package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.*;
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

    @GetMapping("/get/")
    public ResponseEntity<UserProfileDTO> getUserProfileData(@RequestParam String id) throws Exception {
        GlobalValidator.checkIfAFieldIsEmpty(id);
        authService.checkFraudulentRequest(id);
        final User user=userValidator.returnUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                UserMapper.toProfileDTO(user)
        );
    }

    @PostMapping ("/register")
    public ResponseEntity<LoginSuccessDTO> createUser(@RequestBody UserDTO user) throws Exception {
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
                .body(new LoginSuccessDTO(token)
                );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessDTO> loginAuthentication(@RequestBody LoginRequestDTO loginRequestDTO) {
        GlobalValidator.checkIfTwoFieldsAreEmpty(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        final String token=userLogin(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginSuccessDTO(token)
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {


        // FIX

        return ResponseEntity.ok("success");
    }

    @PutMapping("/deactivate/")
    public ResponseEntity <SuccessfulRequestDTO> deactivateAccount(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final User user=userValidator.returnUserById(userId);
        userService.deactivateAccount(userId);
        postService.deactivateAllUserPosts(userId);
        addressService.deactivateAllUserAddresses(userId);
        bankAccountService.deactivateAllUserBankAccounts(userId);
        phoneNumberService.deactivateAllUserPhoneNumbers(userId);
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateDeactivatedAccountParams(user.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessfulRequestDTO("Account successfully deactivated.")
        );
    }

    @GetMapping("/addresses/")
    public ResponseEntity<AddressDTO[]> getUserAddresses(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Address> addresses=userService.getUserAddresses(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(AddressMapper.addressListToAddressDTOArray(addresses)
                );
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<SuccessfulRequestDTO> changeUserEmail(@RequestBody ChangeUserFieldsRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
        authService.checkFraudulentRequest(request.getUserId());
        userService.changeEmail(request.getUserId(), request.getNewField());
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateNewEmailParams(request.getNewField()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessfulRequestDTO("Email address updated successfully."));
    }

    @PutMapping("/change-password")
    public ResponseEntity<SuccessfulRequestDTO> changeUserPassword(@RequestBody ChangePasswordRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        UserValidator.emptyChangePasswordFieldsValidator(request);
        authService.checkFraudulentRequest(request.getId());
        final User foundUser=userValidator.returnUserById(request.getId());
        userService.changeUserPassword(request);
        emailSenderService.sendEmailWithNoAttachment(EmailParamsGenerator.generateNewPasswordParams(foundUser.getEmail()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessfulRequestDTO("Password updated successfully."));
    }

    private String userLogin(String userEmail, String password){
        return authService.authenticateUserAndGenerateToken(userEmail,password);
    }

}
