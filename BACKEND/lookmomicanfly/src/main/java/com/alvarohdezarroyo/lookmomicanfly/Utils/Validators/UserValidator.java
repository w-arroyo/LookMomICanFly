package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.SameValuesException;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangePasswordRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {

    @Autowired
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void emptyUserDTOFieldsValidator(UserDTO user){
        final List<String> errorsList=new ArrayList<>();
        if(user.getEmail()==null || user.getEmail().isBlank())
            errorsList.add("email");
        if(user.getName()==null || user.getName().isBlank())
            errorsList.add("name");
        if (user.getPassword()==null || user.getPassword().isBlank())
            errorsList.add("password");
        if(!errorsList.isEmpty())
            throw new EmptyFieldsException(errorsList);
    }

    public User returnUserById(String id){
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Id does not belong to any user account.")
        );
    }

    public boolean checkUserByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public static void checkIfBothEmailsAreTheSame(String email1, String email2){
        if(email1.trim().equalsIgnoreCase(email2.trim()))
            throw new SameValuesException("New email can not be the same as the current one.");
    }

    public static void emptyChangePasswordFieldsValidator(ChangePasswordRequestDTO request){
        final List<String> errorsList=new ArrayList<>();
        if(request.getId()==null || request.getId().isBlank())
            errorsList.add("id");
        if(request.getOldPassword()==null || request.getOldPassword().isBlank())
            errorsList.add("oldPassword");
        if (request.getNewPassword()==null || request.getNewPassword().isBlank())
            errorsList.add("newPassword");
        if(!errorsList.isEmpty())
            throw new EmptyFieldsException(errorsList);
    }

}
