package com.alvarohdezarroyo.lookmomicanfly.Utils.Validators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.InvalidTokenException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenValidator {

    private static Claims extractClaims(String token) {
        try{
            return Jwts.parser()
                    .verifyWith(AppConfig.getTokenSigningSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidTokenException("Invalid or expired token.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getTokenUserId(String token){
        return extractClaims(token).getSubject();
    }

    public static boolean checkIfTokenIsStillValid(String token){
        return extractClaims(token).getExpiration()
                .after(new Date());
    }

    public static void checkFraudulentRequest(String token, String userId){
        if(!getTokenUserId(token).equals(userId))
            throw new FraudulentRequestException("You can not access someone else's data.");
    }

}
