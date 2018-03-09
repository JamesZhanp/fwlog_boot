package com.fwlog.james;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.logging.Logger;


/**
 * @author jamesZhan
 * @date 2018-01-19
 */
@RestController
//这是Spring boot的入口
@SpringBootApplication
@ComponentScan(basePackages = "com.fwlog.james")
public class Application extends WebMvcConfigurerAdapter{

//    private static Logger logger = Logger.getLogger(Application.class);
    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Bean
    public SessionFactory getSessionFactory() {
        /*if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }*/
        return entityManagerFactory.unwrap(SessionFactory.class);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*").allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
            }
        };
    }

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }
}
