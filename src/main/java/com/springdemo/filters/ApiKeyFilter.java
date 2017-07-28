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

import static com.springdemo.shared.constants.Constant.*;

@Component
public class ApiKeyFilter extends GenericFilterBean {
    private static Logger filterLogger = LogManager.getLogger(ApiKeyFilter.class);

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

            if (req.getHeader(X_VSOLV_NONCE) == null ||
                    req.getHeader(X_VSOLV_SIGNATURE) == null) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setContentType(CONTENT_TYPE);
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ReturnCodes.MISSING_HEADER_VALUE.getMessage());
                return;
            }
            else if (!req.getHeader(X_VSOLV_NONCE).contentEquals(applicationProperties.getHttpHeader().getvSolvNonce()) ||
                    !req.getHeader(X_VSOLV_SIGNATURE).contentEquals(applicationProperties.getHttpHeader().getvSolvSignature())) {
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