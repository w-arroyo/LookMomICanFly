package com.alvarohdezarroyo.lookmomicanfly.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailTemplateService {

    @Autowired
    private final TemplateEngine templateEngine;

    public EmailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String addParamsToHTMLTemplate(String template, Map<String,Object> params){
        final Context context=new Context();
        context.setVariables(params);
        return templateEngine.process(template,context);
    }

}
