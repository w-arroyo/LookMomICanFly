package com.alvarohdezarroyo.lookmomicanfly.Utils.Extractors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenExtractor {

    public String getToken(HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        if (header != null && !header.trim().isEmpty() && header.startsWith("Bearer "))
            return header.substring(7); // removes Bearer from the header to get just the token
        return null;
    }

}
