package com.springdemo.app;

import com.vsolv.appframework.http.request.GetJsonArgumentResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.springdemo")
public class SampleApiApplication extends SpringBootServletInitializer {

    private final Logger appLogger = LogManager.getLogger(this.getClass());

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {
        SpringApplication.run(SampleApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // init code goes here
        appLogger.debug(applicationProperties.toString());
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
        /**
         * Enable CORS
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }

        /**
         * Enable GET Json arguments
         */
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new GetJsonArgumentResolver());
        }
    }
}
