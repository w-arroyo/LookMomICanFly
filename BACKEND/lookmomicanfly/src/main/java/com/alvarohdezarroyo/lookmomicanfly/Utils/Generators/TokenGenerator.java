package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    public static String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + AppConfig.getTokenDuration()))
                .signWith(AppConfig.getTokenSigningSecretKey())
                .compact();
    }

}
