package com.alvarohdezarroyo.lookmomicanfly.Config;

import com.alvarohdezarroyo.lookmomicanfly.Resolvers.VisitorInfoArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new VisitorInfoArgumentResolver(AppConfig.getVisitorInfoAtt()));
    }

}
