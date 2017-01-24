package com.springdemo.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        JsonRequestParam queryAnnotation = methodParameter.getParameterAnnotation(JsonRequestParam.class);
        return queryAnnotation != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String[] paramVals = request.getParameterValues(methodParameter.getParameterName());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(paramVals[0], methodParameter.getParameterType());
        } catch (IOException e) {
            return WebArgumentResolver.UNRESOLVED;
        }
    }
}
