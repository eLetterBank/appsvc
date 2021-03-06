package com.springdemo.interceptors.audit;

import com.springdemo.exceptions.ReturnCodes;
import com.springdemo.exceptions.ServiceException;
import com.springdemo.shared.models.AuditEvent;
import com.springdemo.shared.models.ExecutionContext;
import com.springdemo.shared.utilities.auditlog.AuditLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class AuditInterceptor implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger(AuditInterceptor.class);

    @Autowired
    private ExecutionContext executionContext = null;

    @Autowired
    private AuditEvent auditEvent = null;

    @Autowired
    private AuditLogger auditLogger = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Pre-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (executionContext == null)
            throw new ServiceException(ReturnCodes.MISSING_EXECUTION_CONTEXT);

        logger.debug(executionContext.getRequestId());

        if (auditEvent == null)
            throw new ServiceException(ReturnCodes.MISSING_AUDIT_EVENT_CONTEXT);

        if (auditLogger == null)
            throw new ServiceException(ReturnCodes.MISSING_AUDIT_LOGGER);

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class) &&
                method.isAnnotationPresent(Audit.class)) {

            logger.debug(method.getAnnotation(Audit.class).value());

            auditEvent.setHost(request.getRemoteHost());
            auditEvent.setRequestId(executionContext.getRequestId());
            auditEvent.setName(method.getAnnotation(Audit.class).value());
            auditEvent.setServiceContract(method.getDeclaringClass().getSimpleName());
            auditEvent.setRequestInTime(LocalDateTime.now());

            for (int i = 0; i < method.getParameterCount(); i++) {
                //Expected only one parameter. If more than one present, then the last one will be recorded
                auditEvent.setOperation(method.getParameters()[i].getType().getSimpleName());

                //Read parameter values
                logger.info(request.getRequestURI());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("Post-handle");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (executionContext == null)
            throw new ServiceException(ReturnCodes.MISSING_EXECUTION_CONTEXT);

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class) &&
                method.isAnnotationPresent(Audit.class)) {
            logger.debug(method.getAnnotation(Audit.class).value());

            auditEvent.setResponse(method.getReturnType().getSimpleName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("After-completion-handle");

        if (executionContext == null)
            throw new ServiceException(ReturnCodes.MISSING_EXECUTION_CONTEXT);

        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (method.isAnnotationPresent(Audit.class)) {
            auditEvent.setResponseOutTime(LocalDateTime.now());
            auditLogger.logEvent(auditEvent);
        }
    }
}