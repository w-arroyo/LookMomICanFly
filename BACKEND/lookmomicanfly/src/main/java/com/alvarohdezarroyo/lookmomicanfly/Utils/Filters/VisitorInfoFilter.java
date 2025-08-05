package com.alvarohdezarroyo.lookmomicanfly.Utils.Filters;

import com.alvarohdezarroyo.lookmomicanfly.DTO.VisitorInfoDTO;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Extractors.VisitorInfoExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j

public class VisitorInfoFilter extends OncePerRequestFilter {

    @Autowired
    private final VisitorInfoExtractor visitorInfoExtractor;
    private final String visitorInfoAttribute;

    public VisitorInfoFilter(VisitorInfoExtractor visitorInfoExtractor, String value) {
        this.visitorInfoExtractor = visitorInfoExtractor;
        visitorInfoAttribute = value;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfRequestMustBeProcessed(request)) {
            extractVisitorInfo(request);
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkIfRequestMustBeProcessed(HttpServletRequest request) {
        if (!request.getMethod().toUpperCase().contains("POST")) {
            return false;
        }
        if (!request.getRequestURI().toUpperCase().matches(".*/(LOGIN|REGISTER)$")) { // .matches() function requires a regex expression as parameter
            return false;
        }
        return true;
    }

    private void extractVisitorInfo(HttpServletRequest request) {
        final VisitorInfoDTO visitorInfoDTO = visitorInfoExtractor.extractVisitorInfo(request);
        request.setAttribute(visitorInfoAttribute, visitorInfoDTO);
    }

}
