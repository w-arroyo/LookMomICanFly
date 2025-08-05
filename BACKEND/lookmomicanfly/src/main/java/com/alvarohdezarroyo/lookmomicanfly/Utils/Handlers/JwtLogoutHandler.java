package com.alvarohdezarroyo.lookmomicanfly.Utils.Handlers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Services.RedisTokenService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Extractors.JwtTokenExtractor;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.TokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    @Autowired
    private final RedisTokenService redisTokenService;
    private final JwtTokenExtractor jwtTokenExtractor;

    public JwtLogoutHandler(RedisTokenService redisTokenService, JwtTokenExtractor jwtTokenExtractor) {
        this.redisTokenService = redisTokenService;
        this.jwtTokenExtractor = jwtTokenExtractor;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String token = jwtTokenExtractor.getToken(request);
        final String userId = TokenValidator.getTokenUserId(token);
        if (!redisTokenService.removeToken(userId, token)) {
            throw new FraudulentRequestException("You do not have permission to make this request.");
        }
        SecurityContextHolder.clearContext();
    }
}
