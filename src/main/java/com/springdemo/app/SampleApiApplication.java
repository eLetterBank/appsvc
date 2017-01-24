package com.springdemo.app;

import com.springdemo.contracts.QueryConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@ComponentScan("com.springdemo.contracts")
public class SampleApiApplication extends SpringBootServletInitializer {

    /*
    public static void main(String[] args) {
        SpringApplication.run(SampleApiApplication.class, args);
    }
    */

    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {
        SpringApplication.run(SampleApiApplication.class, args);
    }

    /**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SampleApiApplication.class);
    }

    @Configuration
    @EnableWebMvc
    public class WebConfig extends WebMvcConfigurerAdapter {

        @Autowired
        QueryConverter queryConverter;

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }

        @Override
        public void addFormatters(FormatterRegistry registry){
            registry.addConverter(queryConverter);
        }
    }
}
