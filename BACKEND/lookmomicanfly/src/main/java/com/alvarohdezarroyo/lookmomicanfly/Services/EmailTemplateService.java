package com.alvarohdezarroyo.lookmomicanfly.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.Map;

@Service
@Slf4j
public class EmailTemplateService {

    @Autowired
    private final TemplateEngine templateEngine;

    public EmailTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String addParamsToHTMLTemplate(String template, Map<String,Object> params){
        try{
            final Context context=new Context();
            context.setVariables(params);
            return templateEngine.process(template,context);
        }
        catch (TemplateInputException ex){
            log.error("Email template not found.");
            return null;
        }
    }

}
