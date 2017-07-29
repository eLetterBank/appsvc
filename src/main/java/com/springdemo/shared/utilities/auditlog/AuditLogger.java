package com.springdemo.shared.utilities.auditlog;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springdemo.shared.models.AuditEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditLogger {
    private static final Logger logger = LogManager.getLogger(AuditLogger.class);

    public void logEvent(AuditEvent event) {
        try {
            logger.info(event.toString());

//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//            String auditString1 = mapper.writeValueAsString(event);
//            logger.info(auditString1);

        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
