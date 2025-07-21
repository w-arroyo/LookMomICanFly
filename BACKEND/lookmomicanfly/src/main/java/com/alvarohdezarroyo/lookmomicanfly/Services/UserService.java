package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.SameValuesException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangePasswordRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.PasswordUtils;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    UserService(UserValidator userValidator, UserRepository userRepository){
        this.userValidator = userValidator;
        this.userRepository=userRepository;
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void deactivateAccount(String id){
        final User user= userValidator.returnUserById(id);
        if(!user.getActive())
            throw new IllegalArgumentException("Account is already deactivated.");
        if(userRepository.deactivateUserAccount(id)<1)
               throw new EntityNotFoundException("Server error. Try again later.");
    }

    @Transactional
    public void changeEmail(String id, String newEmail){
        final User user=userValidator.returnUserById(id);
        UserValidator.checkIfBothEmailsAreTheSame(user.getEmail(),newEmail);
        if(userValidator.checkUserByEmail(newEmail))
            throw new EmailAlreadyInUseException("Email is already in use.");
        if(userRepository.changeUserEmail(id,newEmail)<1)
            throw new EntityNotFoundException("Server error.");
    }

    @Transactional
    public void changeUserPassword(ChangePasswordRequestDTO request){
        final User user=userValidator.returnUserById(request.getId());
        if(!PasswordUtils.checkPassword(request.getOldPassword(),user.getPassword()))
            throw new IllegalArgumentException("Wrong password.");
        if(PasswordUtils.checkPassword(request.getNewPassword(),user.getPassword()))
            throw new SameValuesException("New password can't be the same as the former one.");
        if(userRepository.changeUserPassword(request.getId(),PasswordUtils.hashPassword(request.getNewPassword()))<1)
            throw new EntityNotFoundException("Server error. Try again later.");
    }

    public List<Address> getUserAddresses(String id){
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Id is not associated with any user account in the DB.")
        ).getAddresses().stream().filter(Address::getActive).toList();
    }

}
