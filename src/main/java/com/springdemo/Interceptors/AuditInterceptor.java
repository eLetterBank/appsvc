package com.springdemo.Interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuditInterceptor implements HandlerInterceptor {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Pre-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        String auditInfo = method.getDeclaringClass().getSimpleName() + ": ";

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {

            if (method.isAnnotationPresent(Audit.class)) {

                logger.debug(method.getAnnotation(Audit.class).value());

                for (int i = 0; i < method.getParameterCount(); i++) {
                    auditInfo = auditInfo + method.getParameters()[i].getType().getSimpleName() + " | ";
                }

                request.setAttribute("AUDIT_INFO", auditInfo);
                request.setAttribute("STARTTIME", System.currentTimeMillis());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("Post-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        String auditInfo = ((String) request.getAttribute("AUDIT_INFO"));

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
            if (method.isAnnotationPresent(Audit.class)) {
                logger.debug(method.getAnnotation(Audit.class).value());

                auditInfo = auditInfo + method.getReturnType().getSimpleName();

                request.setAttribute("AUDIT_INFO", auditInfo);
                request.setAttribute("ENDTIME", System.currentTimeMillis());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("After-completion-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        if (method.isAnnotationPresent(Audit.class)) {
            logger.debug(method.getAnnotation(Audit.class).value());

            logger.warn(method.getAnnotation(Audit.class).value() + ": " + request.getAttribute("AUDIT_INFO"));
            logger.debug("Total Took:" + ((Long) request.getAttribute("ENDTIME") - (Long) request.getAttribute("STARTTIME")));
        }
    }
}