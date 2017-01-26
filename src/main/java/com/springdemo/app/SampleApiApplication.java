package com.springdemo.app;

import com.vsolv.appframework.GetJsonArgumentResolver;
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

import java.util.List;

@SpringBootApplication
@ComponentScan("com.springdemo.contracts")
public class SampleApiApplication extends SpringBootServletInitializer {
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

        /**
        * Enable CORS
        */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }

        /**
         * Enable GET Jason arguments
         */
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new GetJsonArgumentResolver());
        }
    }
}
