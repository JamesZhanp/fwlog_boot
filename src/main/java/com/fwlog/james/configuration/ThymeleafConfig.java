package com.fwlog.james.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.annotation.PostConstruct;

@Configuration
public class ThymeleafConfig {
    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostConstruct
    public void init(){
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver ();

        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("LEGACYHTML5");
        resolver.setOrder(templateEngine.getTemplateResolvers().size());

        templateEngine.addTemplateResolver(resolver);
    }
}
