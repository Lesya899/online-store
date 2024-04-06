package com.example.notificationservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;



@Configuration
@RequiredArgsConstructor
public class TemplateEngineConfig  {
    private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
    private final ApplicationContext applicationContext;

    @Bean
    public SpringTemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(emailTemplateResolver());
        return templateEngine;
    }

    private ITemplateResolver emailTemplateResolver() {
        final SpringResourceTemplateResolver textEmailTemplateResolver = new SpringResourceTemplateResolver();
        textEmailTemplateResolver.setApplicationContext(applicationContext);
        textEmailTemplateResolver.setOrder(1);
        textEmailTemplateResolver.setPrefix("classpath:/templates/");
        textEmailTemplateResolver.setSuffix(".html");
        textEmailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        textEmailTemplateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        textEmailTemplateResolver.setCacheable(false);
        return textEmailTemplateResolver;

    }
}
