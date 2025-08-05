package com.alvarohdezarroyo.lookmomicanfly.Utils.Filters;

import com.alvarohdezarroyo.lookmomicanfly.Services.RedisTokenService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Extractors.JwtTokenExtractor;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final RedisTokenService redisTokenService;
    private final JwtTokenExtractor jwtTokenExtractor;

    public JwtAuthenticationFilter(RedisTokenService redisTokenService, JwtTokenExtractor jwtTokenExtractor) {
        this.redisTokenService = redisTokenService;
        this.jwtTokenExtractor = jwtTokenExtractor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            final String token = jwtTokenExtractor.getToken(request);
            if (token != null && TokenValidator.checkIfTokenIsStillValid(token) && redisTokenService.checkIfTokenIsValid(token)) {
                final String userId= TokenValidator.getTokenUserId(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userId,
                                null, // no need to keep credentials after validation
                                Collections.emptyList()); // no roles
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // add details like IP address
                SecurityContextHolder.getContext().setAuthentication(authentication); // adds the authenticated user to the context allowing him to access secured endpoints
            }
            filterChain.doFilter(request,response); // continues request's flow whether authentication was correct or not
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not allowed.");
        }
    }

}
