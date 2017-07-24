package com.springdemo.filters;

import com.springdemo.app.ApplicationProperties;
import com.springdemo.exceptions.ReturnCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiFilter extends GenericFilterBean {
    private final Logger filterLogger = LogManager.getLogger(this.getClass());

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Autowired
    private ApplicationProperties applicationProperties = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        Map<String, String> reqHeaders = getHeadersInfo(req);


        try
        {
            filterLogger.info(reqHeaders);
            filterLogger.debug(applicationProperties.getHttpHeader().getvSolvNonce());
            filterLogger.debug(applicationProperties.getHttpHeader().getvSolvSignature());

            if (req.getHeader("x-vsolv-nonce") == null ||
                    req.getHeader("x-vsolv-signature") == null) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setContentType(CONTENT_TYPE);
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ReturnCodes.MISSING_HEADER_VALUE.getMessage());
                return;
            }
            else if (!req.getHeader("x-vsolv-nonce").contentEquals(applicationProperties.getHttpHeader().getvSolvNonce()) ||
                    !req.getHeader("x-vsolv-signature").contentEquals(applicationProperties.getHttpHeader().getvSolvSignature())) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setContentType(CONTENT_TYPE);
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ReturnCodes.INVALID_REQUEST.getMessage());
                return;
            }

            chain.doFilter(request, response);
        }
        catch(Exception e)
        {
            filterLogger.error(e.toString());
            filterLogger.error(e);

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json");
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
}