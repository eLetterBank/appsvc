package com.springdemo.interceptors.performance;

import com.springdemo.shared.models.ExecutionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class PerformanceInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(PerformanceInterceptor.class);

    @Autowired
    private ExecutionContext executionContext = null;

    private StopWatch stopWatch = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        StringBuilder sbTaskName = new StringBuilder();

        sbTaskName.append(executionContext.getRequestId());
        sbTaskName.append(" - ");
        sbTaskName.append(method.getDeclaringClass().getSimpleName());
        sbTaskName.append(":");
        sbTaskName.append(method.getName());

        stopWatch = new StopWatch(sbTaskName.toString());
        stopWatch.start("Start: " + method.getName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //Not required.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        stopWatch.stop();
        logger.debug(stopWatch.shortSummary());
    }
}
