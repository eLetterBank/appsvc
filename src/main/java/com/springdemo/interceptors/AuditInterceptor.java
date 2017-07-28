package com.springdemo.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.springdemo.shared.constants.Constant.*;

public class AuditInterceptor implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger(AuditInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Pre-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getSimpleName());
        sb.append(":");

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class) &&
                method.isAnnotationPresent(Audit.class)) {
            logger.debug(method.getAnnotation(Audit.class).value());

            for (int i = 0; i < method.getParameterCount(); i++) {
                sb.append(method.getParameters()[i].getType().getSimpleName());
                sb.append(" | ");

                //Read parameter values
                logger.info(request.getRequestURI());
            }

            request.setAttribute(AUDITINFO, sb.toString());
            request.setAttribute(STARTTIME, System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("Post-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        StringBuilder sb = new StringBuilder();
        sb.append(request.getAttribute(AUDITINFO));

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class) &&
                method.isAnnotationPresent(Audit.class)) {
            logger.debug(method.getAnnotation(Audit.class).value());

            sb.append(method.getReturnType().getSimpleName());

            request.setAttribute(AUDITINFO, sb.toString());
            request.setAttribute(ENDTIME, System.currentTimeMillis());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("After-completion-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        if (method.isAnnotationPresent(Audit.class)) {
            logger.warn(method.getAnnotation(Audit.class).value() + ": " + request.getAttribute(AUDITINFO));
            logger.debug("Total Took:" + ((Long) request.getAttribute(ENDTIME) - (Long) request.getAttribute(STARTTIME)));
        }
    }
}