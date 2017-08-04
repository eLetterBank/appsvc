package com.springdemo.app;

import com.springdemo.interceptors.audit.AuditInterceptor;
import com.springdemo.interceptors.performance.PerformanceInterceptor;
import com.springdemo.shared.models.AuditEvent;
import com.springdemo.shared.models.ExecutionContext;
import com.springdemo.shared.utilities.auditlog.AuditLogger;
import com.vsolv.appframework.http.request.GetJsonArgumentResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.springdemo")
public class SampleApiApplication extends SpringBootServletInitializer {

    private static final Logger appLogger = LogManager.getLogger(SampleApiApplication.class);

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

    //Create Bean method fot "RequestContextListener"
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**
     * Register a RequestContextListener Bean
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(requestContextListener());
    }

    @EnableWebMvc
    @ComponentScan(basePackages = {"com.springdemo"})
    @Configuration
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

        //Create Bean method for "AuditInterceptor"
        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        AuditInterceptor auditInterceptor() {
            return new AuditInterceptor();
        }

        //Create Bean method for "PerformanceInterceptor"
        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        PerformanceInterceptor performanceInterceptor() {
            return new PerformanceInterceptor();
        }

        /**
         * Add AuditInterceptor
         */
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(performanceInterceptor());
            registry.addInterceptor(auditInterceptor());
        }

        //ExecutionContext per session
        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public ExecutionContext executionContext() {
            return new ExecutionContext();
        }

        //AuditEvent per session
        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public AuditEvent auditEvent() {
            return new AuditEvent();
        }

        //AuditLogger per process
        @Bean
        @Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public AuditLogger auditLog() {
            return new AuditLogger();
        }
    }

}
