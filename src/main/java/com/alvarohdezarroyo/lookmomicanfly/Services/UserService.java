package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.SameValuesException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.UserMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
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

    public String returnUserIdByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("User email not found.")
        ).getId();
    }

    @Transactional
    public User saveUser(UserDTO user) {
        try {
            if(userValidator.checkUserByEmail(user.getEmail()))
                throw new EmailAlreadyInUseException("Email is already in use");
            return userRepository.save(UserMapper.toUser(user));
        }
        catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    @Transactional
    public void deactivateAccount(String id){
        try {
            if(!userValidator.checkUserById(id))
                throw new EntityNotFoundException("ID does not belong to any user account.");
            if(userRepository.deactivateUserAccount(id)<1)
               throw new EntityNotFoundException("Server error. Try again later.");
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional
    public void changeEmail(String id, String newEmail){
        System.out.println(id+" "+newEmail);
        try{
            final User user=userRepository.findById(id)
                    .orElseThrow(()->new EntityNotFoundException("User ID not found."));
            GlobalValidator.checkFraudulentRequest(id, user.getId());
            UserValidator.checkIfBothEmailsAreTheSame(user.getEmail(),newEmail);
            if(userValidator.checkUserByEmail(newEmail))
                throw new EmailAlreadyInUseException("Email is already in use.");
            if(userRepository.changeUserEmail(id,newEmail)<1)
                throw new EntityNotFoundException("Server error.");
        } catch (SameValuesException ex){
            throw new SameValuesException(ex.getMessage());
        } catch (FraudulentRequestException ex){
            throw new FraudulentRequestException(ex.getMessage());
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    // change user password method left. could be cool to create a random one and send it in an email to the user

    public AddressDTO[] getUserAddresses(String id) throws Exception {
        final List<Address> addresses = userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Id is not associated with any user account in the DB.")
        ).getAddresses();
        addresses.removeIf(address -> !address.getActive());
        if(addresses.isEmpty())
            return null;
        final AddressDTO [] dtoAddresses = new AddressDTO[addresses.size()];
        for (int address = 0; address < addresses.size(); address++) {
            dtoAddresses[address]= AddressMapper.toDTO(addresses.get(address));
        }
        return dtoAddresses;
    }
}
