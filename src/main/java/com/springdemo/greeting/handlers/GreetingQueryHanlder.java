package com.springdemo.greeting.handlers;

import com.springdemo.exceptions.ReturnCodes;
import com.springdemo.greeting.contracts.queries.GreetingQuery;
import com.springdemo.greeting.contracts.queries.GreetingQueryResult;
import com.springdemo.greeting.repositories.TimeRepository;
import com.springdemo.shared.models.AuditEvent;
import com.springdemo.shared.models.ExecutionContext;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class GreetingQueryHanlder implements QueryHandler<GreetingQuery, GreetingQueryResult> {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TimeRepository timeRepo;

    @Autowired
    private ExecutionContext executionContext = null;

    @Autowired
    private AuditEvent auditEvent = null;

    @Override
    public GreetingQueryResult execute(GreetingQuery qry) {

        if (executionContext == null)
            return new GreetingQueryResult(ReturnCodes.MISSING_EXECUTION_CONTEXT.getMessage(), ReturnCodes.MISSING_EXECUTION_CONTEXT.getId());

        if (auditEvent == null)
            return new GreetingQueryResult(ReturnCodes.MISSING_AUDIT_EVENT_CONTEXT.getMessage(), ReturnCodes.MISSING_AUDIT_EVENT_CONTEXT.getId());

        auditEvent.setOperationHandler(this.getClass().getSimpleName());

        logger.debug("RequestId: " + executionContext.getRequestId());

        if (!isValiedQry(qry)) {
            return new GreetingQueryResult(ReturnCodes.INVALID_REQUEST.getMessage(), ReturnCodes.INVALID_REQUEST.getId());
        }

        String streetName = null;

        if (qry.getAddress() != null) {
            logger.info("Address is avaiable.");
            if (qry.getAddress().getStreet() != null) {
                streetName = qry.getAddress().getStreet();
            }
        }

        String responseData = "Hello " + qry.getName() + " "
                + (streetName == null ? " - " : streetName)
                + "! - "
                + timeRepo.getServerTime().toString()
                + " -> "
                + executionContext.getRequestId();

        return new GreetingQueryResult(responseData, ReturnCodes.SUCCESS.getId());
    }

    private boolean isValiedQry(GreetingQuery qry) {
        return qry != null && qry.getName() != null;
    }
}