package com.alvarohdezarroyo.lookmomicanfly.Resolvers;

import com.alvarohdezarroyo.lookmomicanfly.Annotations.VisitorInfo;
import com.alvarohdezarroyo.lookmomicanfly.DTO.VisitorInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class VisitorInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(VisitorInfoArgumentResolver.class);
    private final String visitorInfoAttribute;

    public VisitorInfoArgumentResolver(String visitorInfoAttribute) {
        this.visitorInfoAttribute = visitorInfoAttribute;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(VisitorInfo.class); // this method tells Spring it supports that annotation
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            VisitorInfo annotation = parameter.getMethodAnnotation(VisitorInfo.class);
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            VisitorInfoDTO visitorInfoDTO = (VisitorInfoDTO) request.getAttribute(visitorInfoAttribute);
            if (visitorInfoDTO == null) {
                throw new IllegalArgumentException("Required info wasn't found inside the request.");
            }
            return visitorInfoDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error reading visitor's info.");
        }

    }
}
