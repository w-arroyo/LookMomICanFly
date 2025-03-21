package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UnauthorizedRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email does not belong to any user."));

        return new org.springframework.security.core.userdetails.User(
                user.getId(), // this sets the id as the username instead of the email
                user.getPassword(),
                Collections.emptyList() // handles roles
        );
    }

    public String getAuthenticatedUserId() {
        checkIfAUserIsLoggedIn();
        return SecurityContextHolder.getContext().getAuthentication().getName(); // gets the username of the logged user. in my case the username is the email
    }

    public void checkIfAUserIsLoggedIn(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            throw new UnauthorizedRequestException("User is not logged in.");
    }

    public void checkFraudulentRequest(String requestUserId){
        if(!requestUserId.equalsIgnoreCase(getAuthenticatedUserId()))
            throw new FraudulentRequestException("User sending the request does not have permission.");
    }

    public void checkIfAUserIsAdmin(){
        if(!userRepository.findById(getAuthenticatedUserId()).orElseThrow(
                ()-> new EntityNotFoundException("User id does not exist.")
        ).getUserType().equals(UserType.ADMIN))
            throw new FraudulentRequestException("You do not have permission to make this request.");
    }

}
