package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.LoginUnsuccessfulException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UnauthorizedRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email does not belong to any user."));

        if(!user.getActive())
            throw new LoginUnsuccessfulException("Invalid credentials.");

        return new org.springframework.security.core.userdetails.User(
                user.getId(), // this sets the id as the username instead of the email
                user.getPassword(),
                Collections.emptyList() // handles roles
        );
    }

    public String authenticateUserAndGenerateToken(String email, String password){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            return TokenGenerator.generateToken(authentication.getName());
        } catch (AuthenticationException ex){
            throw new LoginUnsuccessfulException("Wrong credentials.");
        }
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
        if(!requestUserId.equals(getAuthenticatedUserId()))
            throw new FraudulentRequestException("User sending the request does not have permission.");
    }

    public void checkIfAUserIsAdmin(){
        if(!userRepository.findById(getAuthenticatedUserId()).orElseThrow(
                ()-> new EntityNotFoundException("User id does not exist.")
        ).getUserType().equals(UserType.ADMIN))
            throw new FraudulentRequestException("You do not have permission to make this request.");
    }

}
